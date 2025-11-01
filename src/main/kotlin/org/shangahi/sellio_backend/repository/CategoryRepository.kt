package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Category
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface CategoryRepository : JpaRepository<Category, UUID>
