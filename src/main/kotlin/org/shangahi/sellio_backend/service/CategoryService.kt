package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun getAllCategories(): List<Category> = categoryRepository.findAll()
    fun getCategoryById(id: UUID): Category {
        val category = categoryRepository.findByIdOrNull(id) ?: throw CategoryNotFoundException()
        return category
    }
    fun create(request: CategoryRequest): CategoryResponse {
        val saved = categoryRepository.save(request.toEntity())
        return saved.toResponse()
    }
}