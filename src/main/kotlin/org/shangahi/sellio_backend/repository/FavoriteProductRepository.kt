package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.FavoriteProduct
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface FavoriteProductRepository: JpaRepository<FavoriteProduct, UUID>{
    fun findByUserId(userId: UUID): List<FavoriteProduct>
}