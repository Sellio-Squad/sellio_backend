package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.FavoriteProductsResponse
import org.shangahi.sellio_backend.api.mapper.toFavoriteProductsResponse
import org.shangahi.sellio_backend.repository.FavoriteProductRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class FavoriteProductService(private val favoriteProductRepository: FavoriteProductRepository) {

    @Transactional(readOnly = true)
    fun getFavoriteProductsByUserId(userId: UUID): List<FavoriteProductsResponse> {
        return favoriteProductRepository.findByUserId(userId).map { product -> product.toFavoriteProductsResponse() }
    }
}