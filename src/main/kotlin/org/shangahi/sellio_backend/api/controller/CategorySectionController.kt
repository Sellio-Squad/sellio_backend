package org.shangahi.sellio_backend.api.controller

import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.CategorySectionRequest
import org.shangahi.sellio_backend.api.dto.request.EditCategorySectionRequest
import org.shangahi.sellio_backend.api.dto.response.CategorySectionResponse
import org.shangahi.sellio_backend.service.CategorySectionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/category-sections")
class CategorySectionController(
    private val categorySectionService: CategorySectionService,
) {
    @GetMapping("/active")
    fun getAllActiveCategorySections(): ResponseEntity<List<CategorySectionResponse>> {
        val categorySections = categorySectionService.getActiveCategorySections()
        return ResponseEntity.ok(categorySections)
    }

    @PostMapping()
    fun createCategorySection(@Valid @RequestBody request: CategorySectionRequest): ResponseEntity<List<CategorySectionResponse>> {
        val categorySections = categorySectionService.createCategorySection(request)
        return ResponseEntity.ok(categorySections)
    }

    @PutMapping("/{id}")
    fun editCategorySection(
        @PathVariable id: String,
        @Valid @RequestBody request: EditCategorySectionRequest
    ): ResponseEntity<List<CategorySectionResponse>> {
        val categorySections = categorySectionService.editCategorySection(id, request)
        return ResponseEntity.ok(categorySections)

    }

    @DeleteMapping("/{id}")
    fun deleteCategorySection(@PathVariable id: String): ResponseEntity<List<CategorySectionResponse>> {
        val categorySections = categorySectionService.deleteCategorySection(id)
        return ResponseEntity.ok(categorySections)
    }

}