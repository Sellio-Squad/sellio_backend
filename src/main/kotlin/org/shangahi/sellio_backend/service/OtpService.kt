package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpLog
import org.shangahi.sellio_backend.repository.OtpLogRepository
import org.shangahi.sellio_backend.security.service.otp.OtpGenerator
import org.shangahi.sellio_backend.service.exception.*
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class OtpService(
    private val otpGenerator: OtpGenerator,
    private val otpLogRepository: OtpLogRepository
) {
    @Transactional
    fun createOtp(sessionId: UUID): OtpLog {
        val otp = otpGenerator.generateOtp()

        return otpLogRepository.save(
            OtpLog(
                otp = otp,
                sessionId = sessionId,
                expireAt = Instant.now().plusSeconds(OTP_SECONDS)
            )
        )
    }

    @Transactional
    fun verifyOtp(sessionId: UUID, otp: String): OtpLog {
        val otpLog = otpLogRepository.findValidOtpForSession(
            sessionId,
            Instant.now()
        ) ?: throw OtpExpiredException()

        if (otpLog.otp != otp)
            throw OtpMismatchException()

        otpLog.isVerified = true
        return otpLogRepository.save(otpLog)
    }

    @Scheduled(cron = "0 */25 * * * *")
    fun deleteExpiredOtps() {
        otpLogRepository.deleteExpiredOtps(Instant.now())
    }

    companion object {
        private const val OTP_SECONDS = 60L
    }
}
