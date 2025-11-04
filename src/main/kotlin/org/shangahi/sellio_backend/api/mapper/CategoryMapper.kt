package org.shangahi.sellio_backend.api.mapper

import org.shangahi.sellio_backend.api.dto.response.CategoryDTO
import org.shangahi.sellio_backend.entity.Category

fun Category.toDTO(): CategoryDTO {
    return CategoryDTO(
        id = this.id!!,
        title = this.title,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
        subCategories = this.subCategories.map { it.toDTO() }
    )
}