package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.util.SELLIO_STORE_ID
import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryAlreadyExistException
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {

    @Transactional(readOnly = true)
    fun getAllCategories(): List<Category> = categoryRepository.findAll()

    @Transactional(readOnly = true)
    fun getCategoryById(id: UUID): Category {
        return categoryRepository.findByIdOrNull(id) ?: throw CategoryNotFoundException()
    }

    @Transactional
    fun create(request: CategoryRequest): Category {
        if (categoryRepository.existsByTitle(request.title)) {
            throw CategoryAlreadyExistException()
        }
        return categoryRepository.save(request.toEntity())
    }

    @Transactional(readOnly = true)
    fun getCustomProductCategories(): List<Category> {
        return categoryRepository.findCategoriesByStoreId(SELLIO_STORE_ID)
    }
    @Transactional
    fun deleteCategory(id: UUID) {
        val category = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException()
        categoryRepository.delete(category)
    }
}
