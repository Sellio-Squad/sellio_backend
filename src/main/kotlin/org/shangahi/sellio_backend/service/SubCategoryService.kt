package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.response.SubCategoryDTO
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(private val subCategoryRepository: SubCategoryRepository) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategoryDTO> =
        subCategoryRepository.findByCategoryId(categoryId).map { it.toResponse() }

    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategoryDTO> {
        return subCategoryRepository.findAllByStoreId(storeId).map { it.toResponse() }
    }
}