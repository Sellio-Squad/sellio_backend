package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpAbuse
import org.shangahi.sellio_backend.repository.OtpAbuseRepository
import org.shangahi.sellio_backend.service.exception.OtpBlockedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

@Service
class OtpAbuseService(
    private val otpAbuseRepository: OtpAbuseRepository
) {
    @Transactional
    fun create(phoneNumber: String): OtpAbuse {
        return otpAbuseRepository.findByPhoneNumber(phoneNumber)
            ?: otpAbuseRepository.save(OtpAbuse(phoneNumber = phoneNumber))
    }

    @Transactional
    fun ensureNotBlocked(abuse: OtpAbuse) {
        if (abuse.blockedUntil != null) {

            if (abuse.blockedUntil!!.isAfter(Instant.now())) {
                throw OtpBlockedException()
            }

            abuse.blockedUntil = null
            abuse.attemptCount = 0
            abuse.resendCount = 0
            abuse.updatedAt = Instant.now()
            otpAbuseRepository.save(abuse)
        }
    }

    @Transactional
    fun onOtpMismatch(abuse: OtpAbuse) {
        abuse.attemptCount++
        if (abuse.attemptCount >= MAX_ATTEMPTS) {
            abuse.blockedUntil = Instant.now().plusSeconds(BLOCK_DURATION)
        }
        otpAbuseRepository.save(abuse)
    }

    @Transactional
    fun onOtpResend(abuse: OtpAbuse) {
        abuse.resendCount++
        abuse.attemptCount = 0
        if (abuse.resendCount >= MAX_ATTEMPTS) {
            abuse.blockedUntil = Instant.now().plusSeconds(BLOCK_DURATION)
        }
        otpAbuseRepository.save(abuse)
    }

    fun onOtpSuccess(phoneNumber: String) {
        otpAbuseRepository.deleteByPhoneNumber(phoneNumber)
    }

    companion object {
        private const val MAX_ATTEMPTS = 3
        private const val BLOCK_DURATION = 2 * 60 * 60L
    }
}
