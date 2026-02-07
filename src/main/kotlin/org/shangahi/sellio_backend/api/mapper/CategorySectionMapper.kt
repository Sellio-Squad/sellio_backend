package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.request.CategorySectionRequest
import org.shangahi.sellio_backend.api.dto.response.CategorySectionResponse
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.entity.CategorySection
import java.util.UUID

fun CategorySection.toResponse(subCategories: List<SubCategoryResponse>): CategorySectionResponse = CategorySectionResponse(
    id = id.toString(),
    sectionTitle = sectionTitle,
    categoryId = categoryId.toString(),
    sortOrder = sortOrder,
    subCategories = subCategories,
    isActive = isActive
)


fun CategorySectionRequest.toEntity(): CategorySection = CategorySection(
    sectionTitle = sectionTitle,
    categoryId = UUID.fromString(categoryId),
    sortOrder = sortOrder,
    isActive = isActive
)
