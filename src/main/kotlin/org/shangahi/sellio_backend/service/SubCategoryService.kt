package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toDTO
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(private val subCategoryRepository: SubCategoryRepository) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategoryResponse> =
        subCategoryRepository.findByCategoryId(categoryId).map { it.toDTO() }

    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategoryResponse> {
        return subCategoryRepository.findAllByStoreId(storeId).map { it.toDTO() }
    }
}