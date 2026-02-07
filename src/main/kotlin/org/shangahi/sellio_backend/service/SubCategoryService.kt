package org.shangahi.sellio_backend.service

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.api.dto.request.EditSubCategoryRequest
import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.entity.SubCategory
import org.shangahi.sellio_backend.repository.*
import org.shangahi.sellio_backend.service.exception.CategoryNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.shangahi.sellio_backend.service.exception.SubCategoryAlreadyExistException
import org.shangahi.sellio_backend.service.exception.SubCategoryNotFoundException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant
import java.util.*

@Service
class SubCategoryService(
    private val subCategoryRepository: SubCategoryRepository,
    private val categoryRepository: CategoryRepository,
    private val storeRepository: StoreRepository,
    private val discountRepository: DiscountRepository,
    private val productSubcategoryRepository: ProductSubcategoryRepository,
    private val storageService: StorageService
) {
    @Transactional
    fun deleteSubCategory(id: UUID) {
        val subCategory = subCategoryRepository.findById(id)
            .orElseThrow { SubCategoryNotFoundException() }
        if (subCategory.imageUrl != null) {
            storageService.deleteImage(subCategory.imageUrl)
        }
        discountRepository.deleteBySubCategoryId(id)
        productSubcategoryRepository.deleteBySubCategoryId(id)
        subCategoryRepository.delete(subCategory)
    }

    @Transactional
    fun getSubCategoriesByCategoryId(categoryId: UUID): List<SubCategoryResponse> {
        if (!categoryRepository.existsById(categoryId)) {
            throw CategoryNotFoundException()
        }
        return subCategoryRepository.findByCategoryId(categoryId).map { it.toResponse() }
    }

    @Transactional
    fun getSubCategoriesByStoreId(storeId: UUID): List<SubCategoryResponse> {
        if (!storeRepository.existsById(storeId)) {
            throw StoreNotFoundException()
        }
        return subCategoryRepository.findAllByStoreId(storeId).map { it.toResponse() }
    }

    @Transactional
    fun create(request: SubCategoryRequest, imageFile: MultipartFile): SubCategory {
        val category = categoryRepository.findById(request.categoryId)
            .orElseThrow { CategoryNotFoundException() }

        if (subCategoryRepository.existsByTitleAndCategoryId(request.title, request.categoryId)) {
            throw SubCategoryAlreadyExistException()
        }
        val safeTitle = request.title.replace(" ", "_")
        val imageUrl = storageService.uploadImage(
            imageFile,
            fileName = "SubCategory_${safeTitle}_${Instant.now().toEpochMilli()}",
            folderName = "SubCategories"
        )
        val subCategory = SubCategory(
            title = request.title,
            imageUrl = imageUrl,
            category = category
        )

        return subCategoryRepository.save(subCategory)
    }

    @Transactional
    fun updateSubCategory(id: UUID, request: EditSubCategoryRequest, imageFile: MultipartFile?): SubCategory {
        val subCategory = subCategoryRepository.findById(id)
            .orElseThrow { SubCategoryNotFoundException() }
        val category = if (request.categoryId != null) {
            categoryRepository.findById(request.categoryId)
                .orElseThrow { CategoryNotFoundException() }
        } else subCategory.category
        if (request.title != null &&
            request.title != subCategory.title &&
            subCategoryRepository.existsByTitleAndCategoryId(request.title, category.id!!)
        ) {
            throw SubCategoryAlreadyExistException()
        }
        val title = request.title ?: subCategory.title
        val safeTitle = title.replace(" ", "_")
        val newImageUrl = if (imageFile != null && !imageFile.isEmpty) {
            if (subCategory.imageUrl != null) {
                storageService.deleteImage(subCategory.imageUrl)
            }
            storageService.uploadImage(
                imageFile,
                fileName = "SubCategory_${safeTitle}_${Instant.now().toEpochMilli()}",
                folderName = "SubCategories"
            )
        } else {
            subCategory.imageUrl
        }
        val updatedSubCategory = subCategory.copy(
            title = title,
            category = category,
            imageUrl = newImageUrl
        )
        return subCategoryRepository.save(updatedSubCategory)
    }
}
