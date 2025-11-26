package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.StoreContactResponse
import org.shangahi.sellio_backend.entity.StoreContact

fun StoreContact.toStoreContactResponse(): StoreContactResponse {
    return StoreContactResponse(
        id = this.id!!,
        storeId = this.store.id!!,
        type = this.type,
        value = this.value
    )
}