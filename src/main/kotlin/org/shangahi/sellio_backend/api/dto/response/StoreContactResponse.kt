package org.shangahi.sellio_backend.api.dto.response

import org.shangahi.sellio_backend.model.ContactType
import java.util.UUID

data class StoreContactResponse(
    val id: UUID,
    val storeId: UUID,
    val type: ContactType,
    val value: String
)
