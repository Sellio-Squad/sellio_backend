package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.ProductCardResponse
import org.shangahi.sellio_backend.api.dto.response.ProductResponse
import org.shangahi.sellio_backend.api.mapper.toProductCardResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.entity.FavoriteProduct
import org.shangahi.sellio_backend.repository.FavoriteProductRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FavoriteProductService(
    private val favoriteProductRepository: FavoriteProductRepository,
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository,
) {

    @Transactional(readOnly = true)
    fun getFavoriteProductsByUserId(userId: UUID, pageable: Pageable): Page<ProductResponse> {

        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException()
        }

        val favoritesPage = favoriteProductRepository.findByUserId(userId, pageable)

        return favoritesPage.map { favProduct ->
            favProduct.product.toResponse(isFavorite = true)
        }
    }

    @Transactional
    fun toggleFavorite(
        productId: UUID,
        userId: UUID
    ): String {

        val user = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        val product = productRepository.findByIdOrNull(productId)
            ?: throw ProductNotFoundException()

        val existingFavorite = favoriteProductRepository.findByUserIdAndProductId(userId, productId)

        return if (existingFavorite != null) {
            favoriteProductRepository.deleteByUserIdAndProductId(userId, productId)
            "Product removed from favorites successfully."
        } else {
            val newFavorite = FavoriteProduct(
                user = user,
                product = product
            )
            favoriteProductRepository.save(newFavorite)
            "Product added to favorites successfully."
        }
    }
}