package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.entity.PendingRegistration
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.PendingRegistrationRepository
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class RegisterService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
    private val refreshTokenService: RefreshTokenService,
    private val otpService: OtpService,
    private val phoneNumberValidator: PhoneNumberValidatorService,
    private val smsSender: SmsSender,
    private val pendingRegistrationRepository: PendingRegistrationRepository
) {

    fun prepareRegistration(request: CreateUserRequest): OtpRequestResponse {
        val validated = phoneNumberValidator.validate(request.phoneNumber, request.region)

        if (userService.findUserByPhoneNumber(validated.phoneNumber) != null)
            throw UserPhoneNumberAlreadyExistsException()

        pendingRegistrationRepository.findByPhoneNumber(validated.phoneNumber)?.let {
            pendingRegistrationRepository.delete(it)
        }

        val pending = PendingRegistration(
            firstName = request.firstName,
            lastName = request.lastName,
            phoneNumber = validated.phoneNumber,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            city = request.city,
            country = request.country,
            avatarUrl = request.avatarUrl
        )
        val savedPending = pendingRegistrationRepository.save(pending)

        val otpLog = otpService.createOtp(
            validated.phoneNumber,
            savedPending.sessionId ?: throw SessionIdNotFoundException()
        )

        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)
        return OtpRequestResponse(otpLog.sessionId.toString())
    }

    fun verifyOtpAndCreateUser(sessionId: String, otp: String): AuthResponse {
        val uuid = UUID.fromString(sessionId)

        otpService.verifyOtp(uuid, otp)

        val pending = pendingRegistrationRepository.findById(uuid)
            .orElseThrow { SessionIdNotFoundException() }

        val existing = userService.findUserByPhoneNumber(pending.phoneNumber)

        if (existing != null && !existing.isDeleted)
            throw UserPhoneNumberAlreadyExistsException()

        val deletedUser = userService.findDeletedUserByPhoneNumber(pending.phoneNumber)

        val savedUser = if (deletedUser != null) {
            val restored = deletedUser.copy(
                isDeleted = false,
                deletedAt = null,
                firstName = pending.firstName,
                lastName = pending.lastName,
                email = pending.email,
                city = pending.city,
                country = pending.country,
                avatarUrl = pending.avatarUrl,
                password = pending.password,
                updatedAt = Instant.now()
            )
            userService.saveUser(restored)
        } else {
            val user = User(
                phoneNumber = pending.phoneNumber,
                password = pending.password,
                firstName = pending.firstName,
                lastName = pending.lastName,
                city = pending.city,
                country = pending.country,
                email = pending.email,
                avatarUrl = pending.avatarUrl
            )
            userService.createUser(user)
        }

        pendingRegistrationRepository.deleteById(uuid)

        val accessToken = jwtService.generateUserToken(savedUser)
        val refreshToken = refreshTokenService.createRefreshToken(savedUser).refreshToken
        return AuthResponse(accessToken, refreshToken)
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    fun deleteExpiredPendingSignups() {
        val expiryTime = Instant.now().minusSeconds(15 * 60)
        pendingRegistrationRepository.deleteAllByCreatedAtBefore(expiryTime)
    }
}
