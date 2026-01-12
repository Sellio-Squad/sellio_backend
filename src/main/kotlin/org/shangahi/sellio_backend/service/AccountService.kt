package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ChangePhoneRequest
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.repository.OtpLogRepository
import org.shangahi.sellio_backend.security.service.PhoneNumberValidatorService
import org.shangahi.sellio_backend.security.service.otp.SmsSender
import org.shangahi.sellio_backend.service.exception.SamePhoneNumberException
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
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
    private val otpLogRepository: OtpLogRepository
) {

    @Transactional
    fun initiatePhoneNumberChange(userId: UUID, request: ChangePhoneRequest): OtpRequestResponse {
        val validated = phoneNumberValidator.validate(request.phoneNumber, request.region)

        val currentUser = userService.findById(userId)

        if (currentUser.phoneNumber == validated.phoneNumber) {
            throw SamePhoneNumberException()
        }

        if (userService.findUserByPhoneNumber(validated.phoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val sessionId = UUID.randomUUID()

        val otpLog = otpService.createOtp(validated.phoneNumber, sessionId)

        smsSender.sendSms(validated.countryCode, validated.phoneNumber, otpLog.otp)

        return OtpRequestResponse(otpLog.sessionId.toString())
    }

    @Transactional
    fun verifyAndChangePhoneNumber(userId: UUID, sessionId: String, otp: String) {
        val sessionId = UUID.fromString(sessionId)

        otpService.verifyOtp(sessionId, otp)

        val otpLog = otpLogRepository.findLatestBySessionId(sessionId)
            ?: throw SessionIdNotFoundException()
        val newPhoneNumber = otpLog.phoneNumber

        if (userService.findUserByPhoneNumber(newPhoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        val user = userService.findById(userId)

        val updatedUser = user.copy(
            phoneNumber = newPhoneNumber,
            updatedAt = java.time.Instant.now()
        )
        userService.saveUser(updatedUser)
        otpService.expireOtpBySessionId(sessionId)
    }
}