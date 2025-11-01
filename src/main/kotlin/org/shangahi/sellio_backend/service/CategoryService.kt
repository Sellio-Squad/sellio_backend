package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategoryService(private val categoryRepository: CategoryRepository) {
    fun getAllCategories(): List<Category> = categoryRepository.findAll()
    fun getCategoryById(id: UUID): Category = categoryRepository.findById(id).orElse(null)
}