package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.ProductSubCategory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ProductSubcategoryRepository : JpaRepository<ProductSubCategory, UUID>