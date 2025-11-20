package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.swagger.doc.CategoryDoc
import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/category")
@Tag(name = "Category", description = "Endpoints for managing Category")
class CategoryController(private val categoryService: CategoryService) {

    @CategoryDoc.GetAllCategories
    @GetMapping("/all-categories")
    fun getAll(): List<CategoryResponse> = categoryService.getAllCategories().map { it.toResponse() }

    @CategoryDoc.GetCategoryInfo
    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): CategoryResponse = categoryService.getCategoryById(id).toResponse()

    @CategoryDoc.InsertCategory
    @PostMapping("/create")
    fun createCategory(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val saved = categoryService.create(request).toResponse()
        return ResponseEntity.ok(saved)
    }

    @CategoryDoc.GetCustom
    @GetMapping("/custom")
    fun getCustomProductCategories(): List<CategoryResponse> {
        return categoryService.getCustomProductCategories().map { it.toResponse() }
    }
}
