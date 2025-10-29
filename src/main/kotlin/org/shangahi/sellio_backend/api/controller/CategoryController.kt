package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.service.CategoryService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/categories")
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping
    fun getAll(): List<Category> = categoryService.getAllCategories()

    @GetMapping("/{id}")
    fun getById(@PathVariable id: UUID): Category = categoryService.getCategoryById(id)

}
