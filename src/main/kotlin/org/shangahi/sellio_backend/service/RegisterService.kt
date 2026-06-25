package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.entity.OtpSession
import org.shangahi.sellio_backend.entity.PendingRegistration
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.model.Role
import org.shangahi.sellio_backend.model.ValidatedPhoneNumber
import org.shangahi.sellio_backend.repository.PendingRegistrationRepository
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
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
    private val smsSender: SmsSender,
    private val pendingRegistrationRepository: PendingRegistrationRepository,
    private val otpSessionService: OtpSessionService,
    private val otpFlowService: OtpFlowService,
    private val otpAbuseService: OtpAbuseService
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

        val otpLog = otpService.createOtp(uuid)

        smsSender.sendSms(
            phoneNumber = otpSession.phoneNumber,
            message = otpLog.otp
        )

        return OtpResponse(uuid.toString(), otpLog.otp, "OTP  resented successfully")
    }

    @Transactional
    fun verifyOtpAndCreateUser(sessionId: String, otp: String, role: Role): AuthResponse {
        val uuid = UUID.fromString(sessionId)
        val otpSession = otpSessionService.getOtpSession(uuid)
        otpFlowService.verifyOtpForSession(sessionId, otp)

        val pending = pendingRegistrationRepository.findByPhoneNumber(otpSession.phoneNumber)
            ?: throw SessionIdNotFoundException()

        val user = createOrRestoreUser(pending, role)
        pendingRegistrationRepository.delete(pending)

        return AuthResponse(
            jwtService.generateUserToken(user,role),
            refreshTokenService.createRefreshToken(user,role).refreshToken
        )
    }

    @Transactional
    @Scheduled(fixedRate = CLEANUP_INTERVAL_MS)
    fun deleteExpiredPendingSignups() {
        val expiryTime = Instant.now().minusSeconds(PENDING_SIGNUP_EXPIRY_SECONDS)
        pendingRegistrationRepository.deleteAllByCreatedAtBefore(expiryTime)
    }

    private fun createOrRestoreUser(pending: PendingRegistration, role: Role): User {
        val user = userService.findDeletedUserByPhoneNumber(pending.phoneNumber)

        return if (user != null) {
            userService.saveUser(
                user.copy(
                    isDeleted = false,
                    deletedAt = null,
                    fullName = pending.fullName,
                    email = pending.email,
                    city = pending.city,
                    country = pending.country,
                    avatarUrl = pending.avatarUrl,
                    password = pending.password,
                    updatedAt = Instant.now(),
                    roles = assignRole(user.roles, role)
                )
            )
        } else {
            userService.createUser(
                User(
                    roles = setOf(role),
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

    private fun assignRole(existingRoles: Set<Role>, role: Role): Set<Role> {
        return if (role == Role.CUSTOMER) existingRoles else existingRoles + role
    }

    private fun getOtpResponse(
        validated: ValidatedPhoneNumber,
        otpSession: OtpSession
    ): OtpResponse {
        val otpLog = otpService.createOtp(otpSession.sessionId!!)

        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)
        return OtpResponse(otpLog.sessionId.toString(), otpLog.otp, "OTP sent successfully")
    }

    companion object {
        private const val PENDING_SIGNUP_EXPIRY_SECONDS = 15 * 60L
        private const val CLEANUP_INTERVAL_MS = 5 * 60 * 1000L
    }
}
