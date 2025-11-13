package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.WeightRequest
import org.shangahi.sellio_backend.api.mapper.toEntity
import org.shangahi.sellio_backend.entity.Weight
import org.shangahi.sellio_backend.repository.ProductItemRepository
import org.shangahi.sellio_backend.repository.WeightRepository
import org.shangahi.sellio_backend.service.exception.WeightAlreadyExistException
import org.shangahi.sellio_backend.service.exception.WeightInUseException
import org.shangahi.sellio_backend.service.exception.WeightNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class WeightService(
    val weightRepository: WeightRepository,
    val productItemRepository: ProductItemRepository
) {
    @Transactional(readOnly = true)
    fun getAllWeights(): List<Weight> {
        return weightRepository.findAll()
    }

    @Transactional(readOnly = true)
    fun getWeightById(weightId: Int): Weight {
        val weight = weightRepository.findByIdOrNull(weightId)
            ?: throw WeightNotFoundException()
        return weight
    }

    @Transactional
    fun createWeight(request: WeightRequest): Weight {
        if (weightRepository.existsByValue(request.value)) {
            throw WeightAlreadyExistException()
        }

        val newWeight = request.toEntity()
        val savedWeight = weightRepository.save(newWeight)
        return savedWeight
    }

    @Transactional
    fun updateWeight(weightId: Int, request: WeightRequest): Weight {
        val existingWeight = weightRepository.findByIdOrNull(weightId) ?: throw WeightNotFoundException()
        if (weightRepository.existsByValue(request.value)) {
            throw WeightAlreadyExistException()
        }

        val updatedWeight = existingWeight.copy(
            value = request.value
        )
        return updatedWeight
    }

    @Transactional
    fun deleteWeight(weightId: Int): String {
        if (!weightRepository.existsById(weightId)) {
            throw WeightNotFoundException()
        }
        if (productItemRepository.existsByWeightId(weightId)) {
            throw WeightInUseException()
        }
        weightRepository.deleteById(weightId)
        return "Weight was deleted successfully"
    }
}
