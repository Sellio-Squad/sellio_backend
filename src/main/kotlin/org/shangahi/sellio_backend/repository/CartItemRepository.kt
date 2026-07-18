package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.CartItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartItemRepository : JpaRepository<CartItem, UUID> {

    fun findByCartIdAndProductId(cartId: UUID, productId: UUID): CartItem?

    fun findByCartId(cartId: UUID): List<CartItem>

    fun deleteByCartId(cartId: UUID)
}
