package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.Discount
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class DiscountService(
    private val discountRepository: DiscountRepository,
    private val productRepository: ProductRepository,
    private val storeRepository: StoreRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository
) {

    @Transactional(readOnly = true)
    fun getDiscountsByStoreId(storeId: UUID, pageable: Pageable): Page<Discount> =
        fetchOrThrow(
            id = storeId,
            pageable = pageable,
            isExists = storeRepository::existsById,
            fetcher = discountRepository::findByStoreId,
        ) { StoreNotFoundException() }


    @Transactional(readOnly = true)
    fun getDiscountsByProductId(productId: UUID, pageable: Pageable): Page<Discount> =
        fetchOrThrow(
            id = productId,
            pageable = pageable,
            isExists = productRepository::existsById,
            fetcher = discountRepository::findByProductId,
        ) { ProductNotFoundException() }


    @Transactional(readOnly = true)
    fun getDiscountsByCategoryId(categoryId: UUID, pageable: Pageable): Page<Discount> =
        fetchOrThrow(
            id = categoryId,
            pageable = pageable,
            isExists = categoryRepository::existsById,
            fetcher = discountRepository::findByCategoryId,
        ) { CategoryNotFoundException() }


    @Transactional(readOnly = true)
    fun getDiscountsBySubCategoryId(subCategoryId: UUID, pageable: Pageable): Page<Discount> =
        fetchOrThrow(
            id = subCategoryId,
            pageable = pageable,
            isExists = subCategoryRepository::existsById,
            fetcher = discountRepository::findBySubCategoryId,
        ) { SubCategoryNotFoundException() }


    private fun fetchOrThrow(
        id: UUID,
        pageable: Pageable,
        isExists: (UUID) -> Boolean,
        fetcher: (UUID, Pageable) -> Page<Discount>,
        exception: () -> SellioException
    ): Page<Discount> {
        if (isExists(id).not()) throw exception()
        return fetcher(id, pageable)
    }
}