package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.StoreCardResponse
import org.shangahi.sellio_backend.entity.Store
import java.util.UUID
import kotlin.collections.contains
import kotlin.collections.get

fun Store.toStoreCardResponse(
    discountsMap: Map<UUID, Double>,
    favoriteStoreIds: Set<UUID>
): StoreCardResponse{
    return StoreCardResponse(
        id = id,
        title = title,
        coverImageURL = coverImageURL,
        maxDiscount = discountsMap[id],
        isFavorite = favoriteStoreIds.contains(id)
    )
}