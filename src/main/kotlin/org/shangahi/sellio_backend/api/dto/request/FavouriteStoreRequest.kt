package org.shangahi.sellio_backend.api.dto.request

import java.util.*

data class FavouriteStoreRequest(
    val storeId: UUID,
    val userId: UUID
)
