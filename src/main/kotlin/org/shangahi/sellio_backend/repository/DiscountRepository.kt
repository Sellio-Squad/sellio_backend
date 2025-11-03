package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Discount
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

    @Repository
    interface DiscountRepository : JpaRepository<Discount, UUID> {

        fun findByStoreId(storeId: UUID, pageable: Pageable): Page<Discount>
    }
