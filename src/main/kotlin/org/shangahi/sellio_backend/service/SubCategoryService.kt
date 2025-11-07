package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository,
    private val storeRepository: StoreRepository
) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategoryResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }
        if (!storeRepository.existsById(categoryId)) {
            throw StoreNotFoundException()
        }
        return subCategoryRepository.findByCategoryId(categoryId).map { it.toResponse() }
    }

    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategoryResponse> {
        return subCategoryRepository.findAllByStoreId(storeId).map { it.toResponse() }
    }
}