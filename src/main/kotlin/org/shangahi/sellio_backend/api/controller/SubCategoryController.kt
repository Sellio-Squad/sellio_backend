package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.swagger.SubCategoryDoc
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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
}