package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.StoreCategory
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface StoreCategoryRepository : JpaRepository<StoreCategory, UUID>
