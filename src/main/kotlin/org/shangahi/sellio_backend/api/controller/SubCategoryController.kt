package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.EditSubCategoryRequest
import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.SubCategoryDoc
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/subcategories")
@Tag(name = "SubCategory", description = "Endpoints for managing SubCategory")
class SubCategoryController(
    private val subCategoryService: SubCategoryService
) {

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
    fun delete(@PathVariable id: UUID): ResponseEntity<Unit> {
        subCategoryService.deleteSubCategory(id)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/category/{categoryId}/{subCategoryId}")
    fun deleteByCombinedPath(
        @PathVariable categoryId: UUID,
        @PathVariable subCategoryId: UUID
    ): ResponseEntity<Unit> {
        subCategoryService.deleteSubCategory(subCategoryId)
        return ResponseEntity.noContent().build()
    }

    @SubCategoryDoc.InsertSubCategory
    @PostMapping()
    fun create(
        @Valid @RequestPart("data") request: SubCategoryRequest,
        @RequestPart("image") imageFile: MultipartFile
    ): ResponseEntity<SubCategoryResponse> {
        val saved = subCategoryService.create(request, imageFile).toResponse()
        return ResponseEntity.ok(saved)
    }

    @PutMapping("/{id}")
    fun updateSubCategory(
        @PathVariable id: UUID,
        @RequestPart("data") request: EditSubCategoryRequest,
        @RequestPart("image") imageFile: MultipartFile?
    ): ResponseEntity<SubCategoryResponse> {
        val updated = subCategoryService.updateSubCategory(id, request, imageFile)
        return ResponseEntity.ok(updated.toResponse())
    }
}
