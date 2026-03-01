package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ForgotPasswordService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val otpService: OtpService,
    private val smsSender: SmsSender,
    private val phoneNumberValidator: PhoneNumberValidatorService,
    private val passwordEncoder: PasswordEncoder,
    private val otpSessionService: OtpSessionService,
    private val otpAbuseService: OtpAbuseService
) {

    fun requestReset(phoneNumber: String, region: String?): OtpResponse {

        val validated = phoneNumberValidator.validate(phoneNumber, region)

        userService.findUserByPhoneNumber(validated.phoneNumber)
            ?: throw UserNotFoundException()

        val abuse = otpAbuseService.create(validated.phoneNumber)

        otpAbuseService.ensureNotBlocked(abuse)

        val otpSession = otpSessionService.create(validated.phoneNumber)
        val otpLog = otpService.createOtp(otpSession.sessionId!!)

        otpAbuseService.onOtpResend(abuse)

        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)

        return OtpResponse(otpLog.sessionId.toString(), otpLog.otp, "lets create another one to remember")
    }


    fun resetPassword(sessionId: String, newPassword: String) {

        val uuid = UUID.fromString(sessionId)

        val otpSession = otpSessionService.getOtpSession(uuid)

        if (otpSession.verifiedAt == null) {
            throw UnauthorizedException()
        }
        otpSessionService.validateActive(otpSession)

        val user = userService.findUserByPhoneNumber(otpSession.phoneNumber) ?: throw UserNotFoundException()

        val updated = user.copy(password = passwordEncoder.encode(newPassword))
        userRepository.save(updated)
    }
}
