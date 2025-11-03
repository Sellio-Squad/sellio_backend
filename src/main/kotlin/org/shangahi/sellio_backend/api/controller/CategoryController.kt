package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.CategoryResponse
import org.shangahi.sellio_backend.api.mapper.toDTO
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAll(): List<CategoryResponse> = categoryService.getAllCategories().map { it.toDTO() }

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): CategoryResponse = categoryService.getCategoryById(id).toDTO()

}
