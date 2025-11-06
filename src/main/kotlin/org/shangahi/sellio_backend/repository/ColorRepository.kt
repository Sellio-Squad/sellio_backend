package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Color
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface ColorRepository : JpaRepository<Color, UUID>
