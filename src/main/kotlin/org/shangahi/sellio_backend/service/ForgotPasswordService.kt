package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.repository.OtpLogRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ForgotPasswordService(
    private val userService: UserService,
    private val userRepository: UserRepository,
    private val otpService: OtpService,
    private val otpLogRepository: OtpLogRepository,
    private val smsSender: SmsSender,
    private val phoneNumberValidator: PhoneNumberValidatorService,
    private val passwordEncoder: PasswordEncoder
) {

    fun requestReset(phoneNumber: String, region: String?): OtpRequestResponse {
        val validated = phoneNumberValidator.validate(phoneNumber, region)

        userService.findUserByPhoneNumber(validated.phoneNumber)
            ?: throw UserNotFoundException()

        val sessionId = UUID.randomUUID()

        val otpLog = otpService.createOtp(validated.phoneNumber, sessionId)

        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)

        return OtpRequestResponse(otpLog.sessionId.toString())
    }

    fun verifyOtp(sessionId: String, otp: String) {
        val uuid = sessionId.toUUIDOrThrow()
        otpService.verifyOtp(uuid, otp)
    }

    fun resetPassword(sessionId: String, newPassword: String) {

        val uuid = UUID.fromString(sessionId)

        otpService.validateSessionVerified(uuid)

        val otpLog = otpLogRepository.findLatestBySessionId(uuid)
            ?: throw SessionIdNotFoundException()

        val user = userService.findUserByPhoneNumber(otpLog.phoneNumber)
            ?: throw UserNotFoundException()

        val updated = user.copy(password = passwordEncoder.encode(newPassword))
        userRepository.save(updated)

        otpService.expireOtpBySessionId(uuid)
    }

    private fun String.toUUIDOrThrow(): UUID =
        try {
            UUID.fromString(this)
        } catch (ex: IllegalArgumentException) {
            throw SessionIdNotFoundException()
        }
}
