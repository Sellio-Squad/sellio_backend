package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.SubCategoryDoc
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("v1/subcategories")
@Tag(name = "SubCategory", description = "Endpoints for managing SubCategory")
class SubCategoryController(
    private val subCategoryService: SubCategoryService
) {

    @SubCategoryDoc.GetSubCategoryByCategoryId
    @GetMapping("/category/{categoryId}")
    fun getByCategory(@PathVariable categoryId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId).map { it.toResponse() }
    }

    @SubCategoryDoc.GetSubCategoryByStoreId
    @GetMapping("/store/{storeId}")
    fun getByStoreId(@PathVariable storeId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByStoreId(storeId).map { it.toResponse() }
    }

    @SubCategoryDoc.InsertSubCategory
    @PostMapping("/create")
    fun create(@RequestBody request: SubCategoryRequest): ResponseEntity<SubCategoryResponse> {
        val category = subCategoryService.create(request).toResponse()
        return ResponseEntity.ok(category)
    }
}