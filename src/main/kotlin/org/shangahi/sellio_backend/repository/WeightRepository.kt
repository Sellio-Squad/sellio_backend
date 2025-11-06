package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Weight
import org.springframework.data.jpa.repository.JpaRepository

interface WeightRepository : JpaRepository<Weight, Int>
