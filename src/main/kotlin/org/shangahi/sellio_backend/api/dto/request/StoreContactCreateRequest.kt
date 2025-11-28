package org.shangahi.sellio_backend.api.dto.request

import org.shangahi.sellio_backend.model.ContactType

data class StoreContactCreateRequest(
    val type: ContactType,
    val value: String
)
