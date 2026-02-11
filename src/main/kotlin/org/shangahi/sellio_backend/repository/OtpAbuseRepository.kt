package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.OtpAbuse
import org.springframework.data.jpa.repository.JpaRepository

interface OtpAbuseRepository : JpaRepository<OtpAbuse, String> {
    fun findByPhoneNumber(phoneNumber: String): OtpAbuse?
    fun deleteByPhoneNumber(phoneNumber: String)
}