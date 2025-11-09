package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAll(): List<CategoryResponse> = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): CategoryResponse = categoryService.getCategoryById(id)

    @PostMapping("/create")
    fun createCategory(@RequestBody request: CategoryRequest): ResponseEntity<CategoryResponse> {
        val saved = categoryService.create(request)
        return ResponseEntity.ok(saved)
    }
}
