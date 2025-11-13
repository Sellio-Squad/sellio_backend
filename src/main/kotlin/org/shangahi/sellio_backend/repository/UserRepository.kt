package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface  UserRepository: JpaRepository<User, UUID> {
    fun existsByPhoneNumber(phoneNumber: String): Boolean
    fun existsByEmail(email: String): Boolean
}