package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.SubCategory
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(private val subCategoryRepository: SubCategoryRepository) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategory> =
        subCategoryRepository.findByCategoryId(categoryId)
}