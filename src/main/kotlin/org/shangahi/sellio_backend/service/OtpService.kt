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
    fun createOtp(phoneNumber: String, sessionId: UUID): OtpLog {
        val otp = otpGenerator.generateOtp()

        return otpLogRepository.save(
            OtpLog(
                phoneNumber = phoneNumber,
                otp = otp,
                sessionId = sessionId
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
        val cutoff = Instant.now().minus(1, ChronoUnit.HOURS)
        otpLogRepository.deleteByCreatedAtBefore(cutoff)
    }

}
