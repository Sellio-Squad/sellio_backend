package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.dto.request.EditCategoryRequest
import org.shangahi.sellio_backend.api.util.SELLIO_STORE_ID
import org.shangahi.sellio_backend.entity.Category
import org.shangahi.sellio_backend.repository.CategoryRepository
import org.shangahi.sellio_backend.service.exception.CategoryAlreadyExistException
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.*

@Service
class CategoryService(
    private val categoryRepository: CategoryRepository,
    private val storageService: StorageService
) {

    @Transactional(readOnly = true)
    fun getAllCategories(): List<Category> = categoryRepository.findAll()

    @Transactional(readOnly = true)
    fun getCategoryById(id: UUID): Category {
        return categoryRepository.findByIdOrNull(id) ?: throw CategoryNotFoundException()
    }

    @Transactional
    fun create(request: CategoryRequest, imageFile: MultipartFile): Category {

        if (categoryRepository.existsByTitle(request.title)) {
            throw CategoryAlreadyExistException()
        }
        val safeTitle = request.title.replace(" ", "_")
        val imageUrl = storageService.uploadImage(
            imageFile,
            fileName = "Category_${safeTitle}_${Instant.now().toEpochMilli()}",
            folderName = "Categories"
        )
        val category = Category(
            title = request.title,
            imageUrl = imageUrl
        )
        return categoryRepository.save(category)
    }

    @Transactional
    fun updateCategory(id: UUID, request: EditCategoryRequest, imageFile: MultipartFile?): Category {
        val category = categoryRepository.findByIdOrNull(id) ?: throw CategoryNotFoundException()
        val title = request.title ?: category.title
        if (request.title != null &&
            request.title != category.title &&
            categoryRepository.existsByTitle(request.title)
        ) {
            throw CategoryAlreadyExistException()
        }
        val safeTitle = title.replace(" ", "_")
        val newImageUrl = if (imageFile != null && !imageFile.isEmpty) {
            if (category.imageUrl != null) {
                storageService.deleteImage(category.imageUrl)
            }
            storageService.uploadImage(
                imageFile,
                fileName = "Category_${safeTitle}_${Instant.now().toEpochMilli()}",
                folderName = "Categories"
            )
        } else category.imageUrl
        val updatedCategory = category.copy(
            title = title,
            imageUrl = newImageUrl
        )
        return categoryRepository.save(updatedCategory)
    }

    @Transactional(readOnly = true)
    fun getCustomProductCategories(): List<Category> {
        return categoryRepository.findCategoriesByStoreId(SELLIO_STORE_ID)
    }

    @Transactional
    fun deleteCategory(id: UUID) {
        val category = categoryRepository.findByIdOrNull(id)
            ?: throw CategoryNotFoundException()
        if (category.imageUrl != null) {
            storageService.deleteImage(category.imageUrl)
        }
        categoryRepository.delete(category)
    }
}
