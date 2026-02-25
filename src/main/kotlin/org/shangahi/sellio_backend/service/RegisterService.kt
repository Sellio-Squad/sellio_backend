package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.entity.OtpSession
import org.shangahi.sellio_backend.entity.PendingRegistration
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.ValidatedPhoneNumber
import org.shangahi.sellio_backend.repository.PendingRegistrationRepository
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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
    private val pendingRegistrationRepository: PendingRegistrationRepository,
    private val otpSessionService: OtpSessionService,
    private val otpFlowService: OtpFlowService,
    private val otpAbuseService: OtpAbuseService,
    private val otpClientService: OtpClientService
) {

    @Transactional
    fun prepareRegistration(request: CreateUserRequest): OtpResponse {
        val validated = phoneNumberValidator.validate(request.phoneNumber, request.region)

        if (userService.findUserByPhoneNumber(validated.phoneNumber) != null)
            throw UserPhoneNumberAlreadyExistsException()

        val abuse = otpAbuseService.create(validated.phoneNumber)
        otpAbuseService.ensureNotBlocked(abuse)

        val otpSession = otpSessionService.create(validated.phoneNumber)
        otpSessionService.validateActive(otpSession)
        otpAbuseService.onOtpResend(abuse)


        val pending = pendingRegistrationRepository.findByPhoneNumber(validated.phoneNumber)?.apply {
            fullName = request.fullName
            email = request.email
            city = request.city
            country = request.country
            avatarUrl = request.avatarUrl
            password = passwordEncoder.encode(request.password)
        } ?: PendingRegistration(
            fullName = request.fullName,
            phoneNumber = validated.phoneNumber,
            password = passwordEncoder.encode(request.password),
            email = request.email,
            city = request.city,
            country = request.country,
            avatarUrl = request.avatarUrl,
            countryCode = validated.countryCode
        )

        pendingRegistrationRepository.save(pending)

        return getOtpResponse(validated, otpSession)
    }

    @Transactional
    fun resendOtp(sessionId: String): OtpResponse {
        val uuid = UUID.fromString(sessionId)
        val otpSession = otpSessionService.getOtpSession(uuid)
        otpSessionService.validateActive(otpSession)

        val abuse = otpAbuseService.create(otpSession.phoneNumber)


        otpAbuseService.ensureNotBlocked(abuse)
        otpAbuseService.onOtpResend(abuse)

        val pending = pendingRegistrationRepository.findByPhoneNumber(otpSession.phoneNumber)
            ?: throw SessionIdNotFoundException()

        val otpLog = otpService.createOtp(uuid)

        otpClientService.sendOtp(
            pending.phoneNumber,
            otpLog.otp
        )
        return OtpResponse(uuid.toString())
    }

    @Transactional
    fun verifyOtpAndCreateUser(sessionId: String, otp: String): AuthResponse {
        val uuid = UUID.fromString(sessionId)
        val otpSession = otpSessionService.getOtpSession(uuid)
        otpFlowService.verifyOtpForSession(sessionId, otp)

        val pending = pendingRegistrationRepository.findByPhoneNumber(otpSession.phoneNumber)
            ?: throw SessionIdNotFoundException()

        val user = createOrRestoreUser(pending)
        pendingRegistrationRepository.delete(pending)

        return AuthResponse(
            jwtService.generateUserToken(user),
            refreshTokenService.createRefreshToken(user).refreshToken
        )
    }

    @Transactional
    @Scheduled(fixedRate = CLEANUP_INTERVAL_MS)
    fun deleteExpiredPendingSignups() {
        val expiryTime = Instant.now().minusSeconds(PENDING_SIGNUP_EXPIRY_SECONDS)
        pendingRegistrationRepository.deleteAllByCreatedAtBefore(expiryTime)
    }

    private fun createOrRestoreUser(pending: PendingRegistration): User {
        val deletedUser = userService.findDeletedUserByPhoneNumber(pending.phoneNumber)

        return if (deletedUser != null) {
            userService.saveUser(
                deletedUser.copy(
                    isDeleted = false,
                    deletedAt = null,
                    fullName = pending.fullName,
                    email = pending.email,
                    city = pending.city,
                    country = pending.country,
                    avatarUrl = pending.avatarUrl,
                    password = pending.password,
                    updatedAt = Instant.now()
                )
            )
        } else {
            userService.createUser(
                User(
                    phoneNumber = pending.phoneNumber,
                    password = pending.password,
                    fullName = pending.fullName,
                    city = pending.city,
                    country = pending.country,
                    email = pending.email,
                    avatarUrl = pending.avatarUrl
                )
            )
        }
    }

    private fun getOtpResponse(
        validated: ValidatedPhoneNumber,
        otpSession: OtpSession
    ): OtpResponse {
        val otpLog = otpService.createOtp(otpSession.sessionId!!)

        otpClientService.sendOtp(
            validated.phoneNumber,
            otpLog.otp
        )
        return OtpResponse(otpLog.sessionId.toString())
    }

    companion object {
        private const val PENDING_SIGNUP_EXPIRY_SECONDS = 15 * 60L
        private const val CLEANUP_INTERVAL_MS = 5 * 60 * 1000L
    }
}
