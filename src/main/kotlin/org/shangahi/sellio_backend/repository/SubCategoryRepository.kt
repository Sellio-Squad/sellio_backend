package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.SubCategory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SubCategoryRepository : JpaRepository<SubCategory, UUID> {
    fun findByCategoryId(categoryId: UUID): List<SubCategory>
}