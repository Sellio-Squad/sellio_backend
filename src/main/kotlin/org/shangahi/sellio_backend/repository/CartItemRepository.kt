package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.CartItem
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CartItemRepository : JpaRepository<CartItem, UUID> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findByCartIdAndProductId(cartId: UUID, productId: UUID): CartItem?

    fun findByCartId(cartId: UUID): List<CartItem>

    fun deleteByCartId(cartId: UUID)
}
