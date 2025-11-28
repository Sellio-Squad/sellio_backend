package org.shangahi.sellio_backend.model

import java.util.*

data class StoreContactModel(
    val id: UUID,
    val storeId: UUID,
    val type: ContactType,
    val value: String
)
