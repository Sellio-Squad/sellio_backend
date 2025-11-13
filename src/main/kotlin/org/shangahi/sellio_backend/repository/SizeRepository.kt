package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Size
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface SizeRepository : JpaRepository<Size, Int>{
    fun existsByValue(value: String): Boolean
}
