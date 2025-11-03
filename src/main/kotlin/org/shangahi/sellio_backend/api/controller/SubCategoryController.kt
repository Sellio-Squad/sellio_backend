package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.SubCategoryResponse
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("v1/subcategories")
class SubCategoryController(private val subCategoryService: SubCategoryService) {

    @GetMapping("/category/{categoryId}")
    fun getByCategory(@PathVariable categoryId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId)
    }

    @GetMapping("/store/{storeId}")
    fun getByStoreId(@PathVariable storeId: UUID): List<SubCategoryResponse> {
        return subCategoryService.getSubCategoriesByStoreId(storeId)
    }
}