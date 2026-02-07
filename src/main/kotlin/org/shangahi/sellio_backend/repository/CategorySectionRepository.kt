package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.CategorySection
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface CategorySectionRepository : JpaRepository<CategorySection, UUID>{
    fun findAllByIsActiveTrueOrderBySortOrderAsc(): List<CategorySection>
    fun findBySortOrder(sortOrder: Int?): CategorySection?
}