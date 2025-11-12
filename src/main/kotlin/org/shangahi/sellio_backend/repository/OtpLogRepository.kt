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

    fun findByOtpAndSessionId(otp: String, sessionId: UUID): OtpLog?

    @Transactional
    @Query("SELECT o FROM OtpLog o WHERE o.sessionId = :sessionId ORDER BY o.id DESC")
    fun findLatestBySessionId(sessionId: UUID): OtpLog?

    @Modifying
    @Transactional
    @Query("UPDATE OtpLog o SET o.isVerified = true WHERE o.sessionId = :sessionId")
    fun verifyOtp(sessionId: UUID)

    @Modifying
    @Transactional
    @Query("DELETE FROM OtpLog o WHERE o.expireAt < :now")
    fun deleteExpiredOtpsBefore(now: Instant)


    @Modifying
    @Transactional
    @Query("""
        UPDATE OtpLog o 
        SET o.expireAt = :now
        WHERE o.sessionId = :sessionId
    """)
    fun expireOtpBySessionId(
        @Param("sessionId") sessionId: UUID,
        @Param("now") now: Instant = Instant.now()
    )
}
