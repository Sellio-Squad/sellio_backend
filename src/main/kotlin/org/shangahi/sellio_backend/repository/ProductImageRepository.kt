package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ProductImage
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductImageRepository : JpaRepository<ProductImage, UUID> {
    fun findAllByProductId(productId: UUID): List<ProductImage>
}
