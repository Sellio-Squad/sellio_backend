package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpSession
import org.shangahi.sellio_backend.repository.OtpSessionRepository
import org.shangahi.sellio_backend.service.exception.OtpBlockedException
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.UUID

@Service
class OtpSessionService(
    private val otpSessionRepository: OtpSessionRepository,
) {

    @Transactional
    fun create(phoneNumber: String): OtpSession {
        otpSessionRepository.deleteAllByPhoneNumberAndExpired(phoneNumber, Instant.now())

        val existing = otpSessionRepository.findByPhoneNumber(phoneNumber)

        if (existing != null && existing.expiresAt.isAfter(Instant.now())) {
            ensureNotBlocked(existing)
            onOtpRequested(existing)
            return existing
        }
        val session = OtpSession(
            phoneNumber = phoneNumber,
            createdAt = Instant.now(),
            expiresAt = Instant.now().plusSeconds(OTP_EXPIRE_SECONDS),
            attemptCount = 1
        )
        return otpSessionRepository.save(session)
    }

    fun getOtpSession(sessionId: UUID): OtpSession {
        return otpSessionRepository.findBySessionId(sessionId)
            ?: throw SessionIdNotFoundException()
    }

    fun ensureNotBlocked(session: OtpSession) {
        if (session.blockedUntil?.isAfter(Instant.now()) == true) {
            throw OtpBlockedException()
        }
    }

    @Transactional
    fun onOtpRequested(session: OtpSession) {
        session.attemptCount++

        if (session.attemptCount > MAX_OTP_REQUESTS) {
            session.blockedUntil = Instant.now().plusSeconds(BLOCK_SECONDS)
        }

        otpSessionRepository.save(session)
    }

    @Transactional
    fun markVerified(session: OtpSession) {
        if (session.expiresAt.isBefore(Instant.now())) {
            otpSessionRepository.delete(session)
            throw UnauthorizedException()
        }

        session.verifiedAt = Instant.now()
        otpSessionRepository.save(session)
    }


    companion object {
        private const val MAX_OTP_REQUESTS = 3
        private const val BLOCK_SECONDS = 2 * 60 * 60L
        private const val OTP_EXPIRE_SECONDS = 1 * 60L
    }
}
