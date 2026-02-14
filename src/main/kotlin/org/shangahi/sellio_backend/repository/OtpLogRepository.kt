package org.shangahi.sellio_backend.repository

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.entity.OtpLog
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant
import java.util.*

interface OtpLogRepository : JpaRepository<OtpLog, UUID> {

    @Query(
        """
    SELECT o FROM OtpLog o
    WHERE o.sessionId = :sessionId
      AND o.isVerified = false
       AND o.expireAt > :now
    ORDER BY o.createdAt DESC
"""
    )
    fun findValidOtpForSession(sessionId: UUID, now: Instant): OtpLog?

    @Modifying
    @Transactional
    @Query(
        """
        DELETE FROM OtpLog o
        WHERE o.expireAt <= :now
        """
    )
    fun deleteExpiredOtps(@Param("now") now: Instant)

}
