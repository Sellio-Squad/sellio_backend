package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Color
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ColorRepository : JpaRepository<Color, Int>{
    fun existsByValue(value: String): Boolean
}
