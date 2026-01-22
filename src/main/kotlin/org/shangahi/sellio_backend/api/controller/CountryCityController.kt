package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.response.CitiesResponse
import org.shangahi.sellio_backend.api.dto.response.toResponse
import org.shangahi.sellio_backend.service.CountryCityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("v1/countries")
class CountryCityController(
    private val countryCityService: CountryCityService
) {
    @GetMapping("/{iso2}/cities")
    fun getCitiesByCountryIso2(
        @PathVariable iso2: String
    ): ResponseEntity<CitiesResponse> {
        return ResponseEntity.ok(countryCityService.getCitiesByCountryIso2(iso2).toResponse())
    }
}