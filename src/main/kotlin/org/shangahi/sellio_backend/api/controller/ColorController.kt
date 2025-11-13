package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.ColorRequest
import org.shangahi.sellio_backend.api.dto.response.ColorResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.ColorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/v1/color")
@Tag(name = "Colors", description = "Endpoints for managing product colors")
class ColorController(private val colorService: ColorService) {
    @GetMapping
    fun getAllColors(): List<ColorResponse> {
        val response = colorService.getAllColors().map { it.toResponse() }
        return response
    }

    @GetMapping("/{id}")
    fun getColorById(@PathVariable id: Int): ColorResponse {
        return colorService.getColorById(id).toResponse()
    }

    @PostMapping
    fun createColor(@Valid @RequestBody request: ColorRequest): ResponseEntity<ColorResponse> {
        val color = colorService.createColor(request)
        val location = URI.create("/v1/colors/${color.id}")
        return ResponseEntity.created(location).body(color.toResponse())
    }

    @PutMapping("/{id}")
    fun updateColor(@Valid @RequestBody request: ColorRequest, @PathVariable id: Int): ResponseEntity<ColorResponse> {
        val updateColor = colorService.updateColor(id, request)
        return ResponseEntity.ok(updateColor.toResponse())
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteColor(@PathVariable id: Int): ResponseEntity<String> {
        val message = colorService.deleteColor(id)
        return ResponseEntity.ok(message)
    }
}