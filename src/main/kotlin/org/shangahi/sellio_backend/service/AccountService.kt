package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ChangePhoneRequest
import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.SamePhoneNumberException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(
    private val userService: UserService,
    private val otpService: OtpService,
    private val phoneNumberValidator: PhoneNumberValidatorService,
    private val smsSender: SmsSender,
    private val otpSessionService: OtpSessionService
) {

    @Transactional
    fun initiatePhoneNumberChange(
        userId: UUID,
        request: ChangePhoneRequest
    ): OtpResponse {

        val validated = phoneNumberValidator.validate(
            request.phoneNumber,
            request.region
        )

        val user = userService.findById(userId)

        if (user.phoneNumber == validated.phoneNumber) {
            throw SamePhoneNumberException()
        }

        if (userService.findUserByPhoneNumber(validated.phoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val otpSession = otpSessionService.create(validated.phoneNumber)

        val otpLog = otpService.createOtp(otpSession.sessionId!!)

        smsSender.sendSms(
            validated.countryCode,
            validated.phoneNumber,
            otpLog.otp
        )

        return OtpResponse(
            otpSession.sessionId.toString(),
            otpLog.otp,
            "OTP sent to new phone number"
        )
    }

    @Transactional
    fun verifyAndChangePhoneNumber(
        userId: UUID,
        sessionId: String,
        otp: String
    ) {
        val uuid = UUID.fromString(sessionId)

        val otpSession = otpSessionService.getOtpSession(uuid)

        otpSessionService.ensureNotBlocked(otpSession)

        otpService.verifyOtp(uuid, otp)

        otpSessionService.markVerified(otpSession)

        val newPhoneNumber = otpSession.phoneNumber

        if (userService.findUserByPhoneNumber(newPhoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val user = userService.findById(userId)

        userService.saveUser(
            user.copy(
                phoneNumber = newPhoneNumber,
                updatedAt = java.time.Instant.now()
            )
        )
    }
}
