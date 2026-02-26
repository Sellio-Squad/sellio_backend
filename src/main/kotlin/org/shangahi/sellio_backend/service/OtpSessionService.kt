package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.OtpSession
import org.shangahi.sellio_backend.repository.OtpSessionRepository
import org.shangahi.sellio_backend.service.exception.SessionExpiredException
import org.shangahi.sellio_backend.service.exception.SessionIdNotFoundException
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant
import java.util.*

@Service
class OtpSessionService(
    private val otpSessionRepository: OtpSessionRepository,
) {

    @Transactional
    fun create(phoneNumber: String): OtpSession {
        otpSessionRepository.deleteAllByPhoneNumberAndExpired(phoneNumber, Instant.now())

        val existing = otpSessionRepository.findByPhoneNumber(phoneNumber)

        if (existing != null && existing.expiresAt.isAfter(Instant.now())) {
            validateActive(existing)
            return existing
        }
        val session = OtpSession(
            phoneNumber = phoneNumber,
            createdAt = Instant.now(),
            expiresAt = Instant.now().plusSeconds(OTP_SESSION_EXPIRE_SECONDS),
        )
        return otpSessionRepository.save(session)
    }

    fun getOtpSession(sessionId: UUID): OtpSession {
        return otpSessionRepository.findBySessionId(sessionId)
            ?: throw SessionIdNotFoundException()
    }

    fun validateActive(session: OtpSession) {
        if (session.expiresAt.isBefore(Instant.now())) {
            throw SessionExpiredException()
        }
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
        private const val OTP_SESSION_EXPIRE_SECONDS = 3 * 60 * 60L
    }
}
