package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun getAllCategories(): List<CategoryResponse> = categoryRepository.findAll().map { it.toResponse() }
    fun getCategoryById(id: UUID): CategoryResponse = categoryRepository.findById(id)
        .orElse(null)
        .toResponse()
    fun create(request: CategoryRequest): CategoryResponse {
        val saved = categoryRepository.save(request.toEntity())
        return saved.toResponse()
    }
}