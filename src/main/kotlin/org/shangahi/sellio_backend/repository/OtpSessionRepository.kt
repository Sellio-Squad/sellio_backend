package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.OtpSession
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.UUID

interface OtpSessionRepository : JpaRepository<OtpSession, UUID> {

    fun findByPhoneNumber(phoneNumber: String): OtpSession?

    fun findBySessionId(sessionId: UUID): OtpSession?

    @Modifying
    @Query("DELETE FROM OtpSession o WHERE o.phoneNumber = :phoneNumber AND o.expiresAt <= :now")
    fun deleteAllByPhoneNumberAndExpired(
        @Param("phoneNumber") phoneNumber: String,
        @Param("now") now: Instant
    )
}
