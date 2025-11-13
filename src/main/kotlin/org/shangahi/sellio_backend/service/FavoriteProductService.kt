package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.FavoriteProductRequest
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.toFavoriteProductsResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.entity.FavoriteProduct
import org.shangahi.sellio_backend.repository.FavoriteProductRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
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
    fun getFavoriteProductsByUserId(userId: UUID, pageable: Pageable): PageResponse<FavoriteProductsResponse> {
        if (!userRepository.existsById(userId)) {
            throw UserNotFoundException()
        }
        val response = favoriteProductRepository.findByUserId(userId, pageable)
        return response.toPageResponse { product -> product.toFavoriteProductsResponse() }
    }

    @Transactional
    fun toggleFavorite(request: FavoriteProductRequest): String {

        val user = userRepository.findByIdOrNull(request.userId)
            ?: throw UserNotFoundException()

        val product = productRepository.findByIdOrNull(request.productId)
            ?: throw ProductNotFoundException()

        val existingFavorite = favoriteProductRepository.findByUserIdAndProductId(request.userId, request.productId)

        return if (existingFavorite != null) {
            favoriteProductRepository.deleteByUserIdAndProductId(request.userId, request.productId)
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