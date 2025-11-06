package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toDTO
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository
) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategoryResponse> =
        subCategoryRepository.findByCategoryId(categoryId).map { it.toDTO() }

    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategoryResponse> {
        return subCategoryRepository.findAllByStoreId(storeId).map { it.toDTO() }
    }

    fun create(request: SubCategoryRequest): SubCategoryResponse {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { RuntimeException("Category not found with id ${request.categoryId}") }
        val saved = subCategoryRepository.save(request.toEntity(category))
        return saved.toDTO()
    }
}