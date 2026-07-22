package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.StoreCardResponse
import org.shangahi.sellio_backend.entity.Store
import java.math.BigDecimal
import java.util.UUID
import kotlin.collections.get

fun Store.toStoreCardResponse(
    discountsMap: Map<UUID, BigDecimal>,
    isFavorite: Boolean
): StoreCardResponse{
    return StoreCardResponse(
        id = id,
        title = title,
        coverImageURL = coverImageURL,
        maxDiscount = discountsMap[id],
        isFavorite = isFavorite
    )
}