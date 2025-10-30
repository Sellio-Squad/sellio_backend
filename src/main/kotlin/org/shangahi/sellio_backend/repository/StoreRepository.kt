package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreRepository : JpaRepository<Store, UUID>