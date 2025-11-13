package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.WeightRequest
import org.shangahi.sellio_backend.api.dto.response.WeightResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.WeightService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/v1/weight")
@Tag(name = "weights", description = "Endpoints for managing product weights")
class WeightController(private val weightService: WeightService) {
    @GetMapping
    fun getAllweights(): List<WeightResponse> {
        val response = weightService.getAllWeights().map { it.toResponse() }
        return response
    }

    @GetMapping("/{id}")
    fun getweightById(@PathVariable id: Int): WeightResponse {
        return weightService.getWeightById(id).toResponse()
    }

    @PostMapping
    fun createweight(@Valid @RequestBody request: WeightRequest): ResponseEntity<WeightResponse> {
        val weight = weightService.createWeight(request)
        val location = URI.create("/v1/weights/${weight.id}")
        return ResponseEntity.created(location).body(weight.toResponse())
    }

    @PutMapping("/{id}")
    fun updateweight(
        @Valid @RequestBody request: WeightRequest,
        @PathVariable id: Int
    ): ResponseEntity<WeightResponse> {
        val updateweight = weightService.updateWeight(id, request)
        return ResponseEntity.ok(updateweight.toResponse())
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteweight(@PathVariable id: Int): ResponseEntity<String> {
        val message = weightService.deleteWeight(id)
        return ResponseEntity.ok(message)
    }
}