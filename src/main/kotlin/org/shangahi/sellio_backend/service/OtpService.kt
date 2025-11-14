package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpLog
import org.shangahi.sellio_backend.repository.OtpLogRepository
import org.shangahi.sellio_backend.security.service.otp.OtpGenerator
import org.shangahi.sellio_backend.service.exception.InvalidOtpException
import org.shangahi.sellio_backend.service.exception.OtpExpiredException
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class OtpService(
    private val otpGenerator: OtpGenerator,
    private val otpLogRepository: OtpLogRepository
) {
    fun createOtp(phoneNumber: String, sessionId: UUID): OtpLog {
        val otp = otpGenerator.generateOtp()
        val otpLog = OtpLog(
            phoneNumber = phoneNumber,
            otp = otp,
            expireAt = Instant.now().plusSeconds(OTP_EXPIRY_SECONDS),
            sessionId = sessionId
        )
        return otpLogRepository.save(otpLog)
    }

    fun verifyOtp(sessionId: UUID, otp: String) {
        val otpLog = otpLogRepository.findByOtpAndSessionId(otp, sessionId)
            ?: throw InvalidOtpException()
        checkOtpExpiration(otpLog)
        if (otpLog.isVerified)
            throw InvalidOtpException()

        otpLogRepository.verifyOtp(sessionId)
    }

    fun validateSessionVerified(sessionId: UUID) {
        val otpLog = otpLogRepository.findLatestBySessionId(sessionId)
            ?: throw UnauthorizedException()
        checkOtpExpiration(otpLog)
        if (!otpLog.isVerified) throw UnauthorizedException()
    }

    fun expireOtpBySessionId(sessionId: UUID) {
        otpLogRepository.expireOtpBySessionId(sessionId)
    }

    private fun checkOtpExpiration(otpLog: OtpLog) {
        if (otpLog.expireAt.isBefore(Instant.now()))
            throw OtpExpiredException()
    }

    @Scheduled(cron = "0 0 0 * * *")
    fun deleteExpiredOtps() {
        otpLogRepository.deleteExpiredOtpsBefore(Instant.now())
    }


    companion object {
        private const val OTP_EXPIRY_SECONDS = 4 * 60L
    }
}
