package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.ColorRequest
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.entity.Color
import org.shangahi.sellio_backend.repository.ColorRepository
import org.shangahi.sellio_backend.repository.ProductItemRepository
import org.shangahi.sellio_backend.service.exception.ColorAlreadyExistException
import org.shangahi.sellio_backend.service.exception.ColorInUseException
import org.shangahi.sellio_backend.service.exception.ColorNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class ColorService(
    val colorRepository: ColorRepository,
    val productItemRepository: ProductItemRepository
) {
    @Transactional(readOnly = true)
    fun getAllColors(): List<Color> {
        return colorRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getColorById(colorId: UUID): Color {
        val color = colorRepository.findByIdOrNull(colorId)
            ?: throw ColorNotFoundException()
        return color
    }

    @Transactional
    fun createColor(request: ColorRequest): Color {
        if (colorRepository.existsByValue(request.value)) {
            throw ColorAlreadyExistException()
        }

        val newColor = request.toEntity()
        val savedColor = colorRepository.save(newColor)
        return savedColor
    }

    @Transactional
    fun updateColor(colorId: UUID, request: ColorRequest): Color {
        val existingColor = colorRepository.findByIdOrNull(colorId) ?: throw ColorNotFoundException()
        if (colorRepository.existsByValue(request.value)) {
            throw ColorAlreadyExistException()
        }

        val updatedColor = existingColor.copy(
            value = request.value.trim()
        )
        return updatedColor
    }

    @Transactional
    fun deleteColor(colorId: UUID): String {
        if (!colorRepository.existsById(colorId)) {
            throw ColorNotFoundException()
        }
        if (productItemRepository.existsByColorId(colorId)) {
            throw ColorInUseException()
        }
        colorRepository.deleteById(colorId)
        return "Color was deleted successfully"
    }
}
