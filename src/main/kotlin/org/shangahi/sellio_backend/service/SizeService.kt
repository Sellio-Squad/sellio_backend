package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.SizeRequest
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.entity.Size
import org.shangahi.sellio_backend.repository.ProductItemRepository
import org.shangahi.sellio_backend.repository.SizeRepository
import org.shangahi.sellio_backend.service.exception.SizeAlreadyExistException
import org.shangahi.sellio_backend.service.exception.SizeInUseException
import org.shangahi.sellio_backend.service.exception.SizeNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class SizeService(
    val sizeRepository: SizeRepository,
    val productItemRepository: ProductItemRepository
) {
    @Transactional(readOnly = true)
    fun getAllSizes(): List<Size> {
        return sizeRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getSizeById(sizeId: UUID): Size {
        val size = sizeRepository.findByIdOrNull(sizeId)
            ?: throw SizeNotFoundException()
        return size
    }

    @Transactional
    fun createSize(request: SizeRequest): Size {
        if (sizeRepository.existsByValue(request.value)) {
            throw SizeAlreadyExistException()
        }

        val newSize = request.toEntity()
        val savedSize = sizeRepository.save(newSize)
        return savedSize
    }

    @Transactional
    fun updateSize(sizeId: UUID, request: SizeRequest): Size {
        val existingSize = sizeRepository.findByIdOrNull(sizeId) ?: throw SizeNotFoundException()
        if (sizeRepository.existsByValue(request.value)) {
            throw SizeAlreadyExistException()
        }

        val updatedSize = existingSize.copy(
            value = request.value.trim()
        )
        return updatedSize
    }

    @Transactional
    fun deleteSize(sizeId: UUID): String {
        if (!sizeRepository.existsById(sizeId)) {
            throw SizeNotFoundException()
        }
        if (productItemRepository.existsBySizeId(sizeId)) {
            throw SizeInUseException()
        }
        sizeRepository.deleteById(sizeId)
        return "size was deleted successfully"
    }
}
