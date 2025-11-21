package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.entity.SubCategory
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.shangahi.sellio_backend.service.exception.SubCategoryAlreadyExistException
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubCategoryService(
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository,
    private val storeRepository: StoreRepository

) {
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategory> {
        if (!categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }

        return subCategoryRepository.findByCategoryId(categoryId)
    }

    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategory> {
        if (!storeRepository.existsById(storeId)) {
            throw StoreNotFoundException()
        }
        return subCategoryRepository.findAllByStoreId(storeId)
    }

    fun create(request: SubCategoryRequest): SubCategory {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { CategoryNotFoundException() }
        if (subCategoryRepository.existsByTitle(request.title)) {
            throw SubCategoryAlreadyExistException()
        }
        return subCategoryRepository.save(request.toEntity(category))
    }
}