package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.OfferRequest
import org.shangahi.sellio_backend.api.dto.response.OfferResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.OffersService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestPart
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/v1/offers")
class OffersController(
    private val offersService: OffersService
) {
    @GetMapping
    fun getOffers(): ResponseEntity<List<OfferResponse>> {
        val response = offersService.getOffers().map { offer -> offer.toResponse() }
        return ResponseEntity.ok(response)
    }

    @PostMapping("/create")
    fun createOffer(
        @RequestPart("image")imageFile: MultipartFile,
        @RequestPart("data")request: OfferRequest
    ): ResponseEntity<OfferResponse>{
       val offer =  offersService.createOffer( imageFile, request)
        return ResponseEntity.ok(offer.toResponse())
    }
}