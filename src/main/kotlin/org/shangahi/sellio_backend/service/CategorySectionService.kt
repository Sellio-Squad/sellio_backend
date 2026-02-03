package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CategorySectionRequest
import org.shangahi.sellio_backend.api.dto.request.EditCategorySectionRequest
import org.shangahi.sellio_backend.api.dto.response.CategorySectionResponse
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.repository.CategorySectionRepository
import org.shangahi.sellio_backend.repository.SubCategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.CategorySectionNotFoundException
import org.springframework.stereotype.Service
import java.util.*

@Service
class CategorySectionService(
    private val categorySectionRepository: CategorySectionRepository,
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository
) {
    fun getActiveCategorySections(): List<CategorySectionResponse> {
        val activeCategorySections = categorySectionRepository.findAllByIsActiveTrueOrderBySortOrderAsc()
        if (activeCategorySections.isEmpty()) return emptyList()
        val categoryIds = activeCategorySections.map { it.categoryId }
        val subCategories = subCategoryRepository.findAllByCategoryIdIn(categoryIds)
        return activeCategorySections.map { categorySection ->
            val subCategories = subCategories.filter { it.category.id == categorySection.categoryId }
            categorySection.toResponse(subCategories = subCategories.map { it.toResponse() })
        }
    }

    fun createCategorySection(request: CategorySectionRequest): List<CategorySectionResponse> {
        val categoryId = UUID.fromString(request.categoryId)
        if (!categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }
        categorySectionRepository.save(request.toEntity())
        return getActiveCategorySections()
    }

    fun editCategorySection(id: String, request: EditCategorySectionRequest): List<CategorySectionResponse> {
        val existingCategorySection = categorySectionRepository.findById(UUID.fromString(id)).orElseThrow {
            throw CategorySectionNotFoundException()
        }
        val updatedSection = existingCategorySection.copy(
            sectionTitle = request.sectionTitle,
            categoryId = UUID.fromString(request.categoryId),
            sortOrder = request.sortOrder,
            isActive = request.isActive
        )
        categorySectionRepository.save(updatedSection)
        return getActiveCategorySections()
    }

    fun deleteCategorySection(id: String): List<CategorySectionResponse> {
        if (!categorySectionRepository.existsById(UUID.fromString(id))) {
            throw CategorySectionNotFoundException()
        }

        categorySectionRepository.deleteById(UUID.fromString(id))

        return getActiveCategorySections()
    }
}

