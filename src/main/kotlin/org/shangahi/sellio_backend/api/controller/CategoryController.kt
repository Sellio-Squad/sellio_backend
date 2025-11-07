package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.swagger.CategoryDoc
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("v1/category")
@Tag(name = "Category", description = "Endpoints for managing Category")
class CategoryController(private val categoryService: CategoryService) {


    @CategoryDoc.GetAllCategories
    @GetMapping("/all-categories")
    fun getAll(): List<CategoryResponse> = categoryService.getAllCategories()

    @CategoryDoc.GetCategoryInfo
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): CategoryResponse = categoryService.getCategoryById(id)

}
