package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.dto.request.EditCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.CategoryDoc
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
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
    @PostMapping()
    fun createCategory(
        @Valid @RequestPart("data") request: CategoryRequest,
        @RequestPart("image") imageFile: MultipartFile
    ): ResponseEntity<CategoryResponse> {
        val saved = categoryService.create(request, imageFile).toResponse()
        return ResponseEntity.ok(saved)
    }

    @PutMapping("/{id}")
    fun updateCategory(
        @PathVariable id: UUID,
        @RequestPart("data") request: EditCategoryRequest,
        @RequestPart("image") imageFile: MultipartFile?
    ): ResponseEntity<CategoryResponse> {
        val updated = categoryService.updateCategory(id, request, imageFile).toResponse()
        return ResponseEntity.ok(updated)
    }

    @DeleteMapping("/{id}")
    @CategoryDoc.DeleteCategory
    fun deleteCategory(@PathVariable id: UUID): ResponseEntity<Unit> {
        categoryService.deleteCategory(id)
        return ResponseEntity.noContent().build()
    }

    @CategoryDoc.GetCustom
    @GetMapping("/custom")
    fun getCustomProductCategories(): List<CategoryResponse> {
        return categoryService.getCustomProductCategories().map { it.toResponse() }
    }
}
