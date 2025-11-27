package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.StoreContactCreateRequest
import org.shangahi.sellio_backend.api.dto.response.StoreContactResponse
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.entity.StoreContact
import org.shangahi.sellio_backend.model.StoreContactCreationModel
import org.shangahi.sellio_backend.model.StoreContactModel

fun StoreContact.toStoreContactResponse(): StoreContactResponse {
    return StoreContactResponse(
        id = this.id!!,
        storeId = this.store.id!!,
        type = this.type,
        value = this.value
    )
}

fun StoreContactCreateRequest.toRequest(): StoreContactCreationModel {
    return StoreContactCreationModel(
        type = this.type,
        value = this.value
    )
}

fun StoreContactModel.toResponse(): StoreContactResponse {
    return StoreContactResponse(
        id = this.id,
        storeId = this.storeId,
        type = this.type,
        value = this.value
    )
}