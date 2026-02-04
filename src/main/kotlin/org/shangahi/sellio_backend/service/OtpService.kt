package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpLog
import org.shangahi.sellio_backend.repository.OtpLogRepository
import org.shangahi.sellio_backend.security.service.otp.OtpGenerator
import org.shangahi.sellio_backend.service.exception.InvalidOtpException
import org.shangahi.sellio_backend.service.exception.OtpBlockedException
import org.shangahi.sellio_backend.service.exception.OtpExpiredException
import org.shangahi.sellio_backend.service.exception.OtpMismatchException
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class OtpService(
    private val otpGenerator: OtpGenerator,
    private val otpLogRepository: OtpLogRepository
) {
    fun createOtp(phoneNumber: String, sessionId: UUID): OtpLog {
        otpLogRepository.expireOtpBySessionId(sessionId)

        val otp = otpGenerator.generateOtp()

        val otpLog = OtpLog(
            phoneNumber = phoneNumber,
            otp = otp,
            expireAt = Instant.now().plusSeconds(OTP_EXPIRY_SECONDS),
            sessionId = sessionId
        )
        return otpLogRepository.save(otpLog)
    }

    @Transactional
    fun verifyOtp(sessionId: UUID, otp: String) {
        val otpLog = otpLogRepository.findLatestBySessionId(sessionId)
            ?: throw InvalidOtpException()

        checkOtpExpiration(otpLog)

        checkOtpIsBlocked(otpLog)

        if (otpLog.isVerified)
            throw InvalidOtpException()

        if (otpLog.otp != otp) {
            handleFailedAttempt(otpLog)
            throw OtpMismatchException()
        }

        otpLog.isVerified = true
        otpLog.attemptCount = 0
        otpLog.blockedUntil = null

        otpLogRepository.save(otpLog)
    }

    fun validateSessionVerified(sessionId: UUID) {
        val otpLog = otpLogRepository.findLatestBySessionId(sessionId)
            ?: throw UnauthorizedException()
        checkOtpExpiration(otpLog)
        if (!otpLog.isVerified) throw OtpMismatchException()
    }

    fun expireOtpBySessionId(sessionId: UUID) {
        otpLogRepository.expireOtpBySessionId(sessionId)
    }

    private fun checkOtpExpiration(otpLog: OtpLog) {
        if (otpLog.expireAt.isBefore(Instant.now()))
            throw OtpExpiredException()
    }

    private fun checkOtpIsBlocked(otpLog: OtpLog){
        if (otpLog.blockedUntil != null &&
            otpLog.blockedUntil!!.isAfter(Instant.now())
        ) {
            throw OtpBlockedException()
        }
    }

    @Scheduled(cron = "0 */25 * * * *")
    fun deleteExpiredOtps() {
        otpLogRepository.deleteExpiredOtpsBefore(Instant.now())
    }

    private fun handleFailedAttempt(otpLog: OtpLog) {
        otpLog.attemptCount += 1

        if (otpLog.attemptCount >= MAX_ATTEMPTS) {
            otpLog.blockedUntil = Instant.now()
                .plusSeconds(BLOCK_DURATION_SECONDS)
        }

        otpLogRepository.save(otpLog)
    }



    companion object {
        private const val OTP_EXPIRY_SECONDS = 1 * 60L
        private const val MAX_ATTEMPTS = 3
        private const val BLOCK_DURATION_SECONDS = 3 * 60L
    }
}
