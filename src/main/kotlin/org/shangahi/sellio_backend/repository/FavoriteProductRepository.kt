package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.FavoriteProduct
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
interface FavoriteProductRepository: JpaRepository<FavoriteProduct, UUID>{
    fun findByUserId(userId: UUID,pageable: Pageable): Page<FavoriteProduct>
    fun findByUserIdAndProductId(userId: UUID, productId: UUID): FavoriteProduct?
    fun deleteByProductId(productId: UUID)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun deleteByUserIdAndProductId(userId: UUID, productId: UUID)
}