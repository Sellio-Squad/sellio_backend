package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.PendingRegistration
import org.springframework.data.jpa.repository.JpaRepository
import java.time.Instant
import java.util.*

interface PendingRegistrationRepository : JpaRepository<PendingRegistration, UUID> {
    fun deleteAllByCreatedAtBefore(time: Instant)
}
