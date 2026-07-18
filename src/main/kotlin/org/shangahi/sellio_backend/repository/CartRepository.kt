package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartRepository : JpaRepository<Cart, UUID> {
    fun findByUserId(userId: UUID): Cart?
}
