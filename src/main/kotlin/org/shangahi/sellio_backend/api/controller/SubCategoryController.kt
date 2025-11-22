package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.swagger.doc.SubCategoryDoc
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/subcategories")
@Tag(name = "SubCategory", description = "Endpoints for managing SubCategory")
class SubCategoryController(private val subCategoryService: SubCategoryService) {

    @SubCategoryDoc.GetSubCategoryByCategoryId
    @GetMapping("/category/{categoryId}")
    fun getByCategory(@PathVariable categoryId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId)
    }

    @SubCategoryDoc.GetSubCategoryByStoreId
    @GetMapping("/store/{storeId}")
    fun getByStoreId(@PathVariable storeId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByStoreId(storeId)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Void> {
        subCategoryService.deleteSubCategory(id)
        return ResponseEntity.noContent().build()
    }

    // Support legacy/alternate combined path used by a client: /v1/category/{categoryId}-{subCategoryId}
    // This is a minimal, non-invasive mapping that delegates to the same service method.
    @DeleteMapping("/v1/category/{categoryId}-{subCategoryId}")
    fun deleteByCombinedPath(
        @PathVariable categoryId: UUID,
        @PathVariable subCategoryId: UUID
    ): ResponseEntity<Void> {
        // categoryId is accepted for compatibility but not needed by the deletion logic
        subCategoryService.deleteSubCategory(subCategoryId)
        return ResponseEntity.noContent().build()
    }

    @SubCategoryDoc.InsertSubCategory
    @PostMapping("/create")
    fun create(@RequestBody request: SubCategoryRequest): ResponseEntity<SubCategoryResponse> =
        ResponseEntity.ok(subCategoryService.create(request))
}
