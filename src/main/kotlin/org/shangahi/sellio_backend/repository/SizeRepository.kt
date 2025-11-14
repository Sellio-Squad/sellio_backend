package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Size
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SizeRepository : JpaRepository<Size, Int>{
    fun existsByValue(value: String): Boolean
}
