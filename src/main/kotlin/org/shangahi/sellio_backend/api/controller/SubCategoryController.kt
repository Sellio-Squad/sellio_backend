package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.SubCategoryDTO
import org.shangahi.sellio_backend.api.mapper.toDTO
import org.shangahi.sellio_backend.service.SubCategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/subcategories")
class SubCategoryController(private val subCategoryService: SubCategoryService) {

    @GetMapping("/category/{categoryId}")
    fun getByCategory(@PathVariable categoryId: UUID): List<SubCategoryDTO> {
        return subCategoryService.getSubCategoriesByCategoryId(categoryId).map { it.toDTO() }
    }
}