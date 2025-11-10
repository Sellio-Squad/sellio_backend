package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Store
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, UUID> {

    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN TRUE ELSE FALSE END FROM Store s WHERE s.owner.id = :ownerId")
    fun isExistByOwnerId(@Param("ownerId") ownerId: UUID): Boolean

    fun existsByTitle(title: String): Boolean

    fun findStoresByTitleIgnoreCase(
        pageable: Pageable,
        title: String
    ): Page<Store>

    fun findStoresByTitleContainingIgnoreCaseAndCityIgnoreCase(
        title: String,
        city: String,
        pageable: Pageable
    ): Page<Store>

    fun existsByPhoneNumber(phoneNumber: String): Boolean
}