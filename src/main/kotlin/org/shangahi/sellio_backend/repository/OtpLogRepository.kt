package org.shangahi.sellio_backend.repository

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.entity.OtpLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.Instant
import java.util.*

interface OtpLogRepository : JpaRepository<OtpLog, UUID> {

    @Query(
        """
    SELECT o FROM OtpLog o
    WHERE o.sessionId = :sessionId
      AND o.isVerified = false
    ORDER BY o.createdAt DESC
"""
    )
    fun findValidOtpForSession(sessionId: UUID, now: Instant): OtpLog?

    @Transactional
    @Query("SELECT o FROM OtpLog o WHERE o.sessionId = :sessionId ORDER BY o.id DESC")
    fun findLatestBySessionId(sessionId: UUID): OtpLog?

    fun deleteByCreatedAtBefore(now: Instant)

}
