package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.StoreContact
import org.shangahi.sellio_backend.model.ContactType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface StoreContactRepository : JpaRepository<StoreContact, UUID> {

    fun existsByTypeAndValue(type: ContactType, value: String): Boolean

    fun existsByTypeAndStoreId(type: ContactType, storeId: UUID): Boolean

    fun findAllByStoreId(storeId: UUID): List<StoreContact>
}
