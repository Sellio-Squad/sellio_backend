package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.DiscountResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.mapper.toPageResponse
import org.shangahi.sellio_backend.api.mapper.toDiscountResponse
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.repository.DiscountRepository
import org.shangahi.sellio_backend.repository.ProductRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.ProductNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.shangahi.sellio_backend.service.exception.SubCategoryNotFoundException
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class DiscountService(
    private val discountRepository: DiscountRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository
) {

    fun getDiscountsByStoreId(storeId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!storeRepository.existsById(storeId))
        {
            throw StoreNotFoundException()
        }

        val discountPage = discountRepository.findByStoreId(storeId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }


    fun getDiscountsByProductId(productId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!productRepository.existsById(productId))
        {
            throw ProductNotFoundException()
        }

        val discountPage = discountRepository.findByProductId(productId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }


    fun getDiscountsByCategoryId (categoryId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!categoryRepository.existsById(categoryId))
        {
            throw CategoryNotFoundException()
        }
        val discountPage = discountRepository.findByCategoryId(categoryId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }


    fun getDiscountsBySubCategoryId (subCategoryId: UUID, pageable: Pageable): PageResponse<DiscountResponse> {

        if (!subCategoryRepository.existsById(subCategoryId))
        {
            throw SubCategoryNotFoundException()
        }
        val discountPage = discountRepository.findBySubCategoryId(subCategoryId, pageable)

        return discountPage.toPageResponse { it.toDiscountResponse() }
    }

}