package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Discount
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DiscountRepository : JpaRepository<Discount, UUID>
