package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, UUID> {

    fun findByPhoneNumberAndIsDeletedFalse(phoneNumber: String): User?

    fun findByPhoneNumberAndIsDeletedTrue(phoneNumber: String): User?

    fun existsByPhoneNumberAndIsDeletedFalse(phoneNumber: String): Boolean

    fun existsByEmailAndIsDeletedFalse(email: String): Boolean

    fun findByIdAndIsDeletedFalse(id: UUID): User?
}