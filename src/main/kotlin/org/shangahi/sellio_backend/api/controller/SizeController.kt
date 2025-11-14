package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.SizeRequest
import org.shangahi.sellio_backend.api.dto.response.SizeResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.SizeDoc
import org.shangahi.sellio_backend.service.SizeService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/v1/size")
@Tag(name = "Sizes", description = "Endpoints for managing product sizes")
class SizeController(private val sizeService: SizeService) {
    @SizeDoc.GetAllSizes
    @GetMapping
    fun getAllSizes(): List<SizeResponse> {
        val response = sizeService.getAllSizes().map { it.toResponse() }
        return response
    }

    @SizeDoc.GetSize
    @GetMapping("/{id}")
    fun getSizeById(@PathVariable id: Int): SizeResponse {
        return sizeService.getSizeById(id).toResponse()
    }
    @SizeDoc.InsertSize
    @PostMapping
    fun createSize(@Valid @RequestBody request: SizeRequest): ResponseEntity<SizeResponse> {
        val size = sizeService.createSize(request)
        val location = URI.create("/v1/sizes/${size.id}")
        return ResponseEntity.created(location).body(size.toResponse())
    }

    @SizeDoc.UpdateSize
    @PutMapping("/{id}")
    fun updateSize(@Valid @RequestBody request: SizeRequest, @PathVariable id: Int): ResponseEntity<SizeResponse> {
        val updateSize = sizeService.updateSize(id, request)
        return ResponseEntity.ok(updateSize.toResponse())
    }

    @SizeDoc.DeleteSize
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSize(@PathVariable id: Int): ResponseEntity<String> {
        val message = sizeService.deleteSize(id)
        return ResponseEntity.ok(message)
    }
}