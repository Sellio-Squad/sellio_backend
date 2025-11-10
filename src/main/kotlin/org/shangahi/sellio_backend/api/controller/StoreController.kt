package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import org.shangahi.sellio_backend.api.dto.request.CreateStoreRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.api.dto.response.StoreInfoResponse
import org.shangahi.sellio_backend.api.dto.response.StoreResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.mapper.toStoreResponse
import org.shangahi.sellio_backend.api.swagger.doc.StoreDoc
import org.shangahi.sellio_backend.service.StoreService
import org.springdoc.core.annotations.ParameterObject
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/v1/stores")
@Tag(name = "Store", description = "Endpoints for managing Stores")
class StoreController(
    private val storeService: StoreService
) {
    @StoreDoc.CreateStore
    @PostMapping("/create")
    fun addStore(@RequestBody request: CreateStoreRequest): ResponseEntity<StoreCreationResponse> {

        val response = storeService.createStore(request)

        val location = URI.create("/v1/stores/${response.id}")

        return ResponseEntity
            .created(location)
            .body(response)
    }

    @PostMapping(value = ["/{storeId}/images"], consumes = ["multipart/form-data"])
    fun uploadStoreImages(
        @PathVariable storeId: UUID,
        @RequestPart("avatarImage", required = false) avatarImage: MultipartFile?,
        @RequestPart("coverImage", required = false) coverImage: MultipartFile?
    ): ResponseEntity<StoreResponse> {

        val response = storeService.uploadStoreImages(storeId, avatarImage, coverImage)
        return ResponseEntity.ok(response.toStoreResponse())
    }

    @StoreDoc.SearchByStoreTitle
    @GetMapping("/search")
    fun searchStoresByTitle(
        @RequestParam title: String,
        @RequestParam(required = false) city: String?,
        @ParameterObject
        @PageableDefault(page = 0, size = 20) pageable: Pageable
    ): PageResponse<StoreResponse> {
        val storesPage = storeService.searchStoresByTitle(title, city, pageable)
        return storesPage.toResponse()
    }

    @StoreDoc.GetStoreInfo
    @GetMapping("/{storeId}")
    fun getStoreDetailsById(@PathVariable storeId: UUID): StoreInfoResponse {
        return storeService.getStoreDetailsById(storeId)
    }

    @StoreDoc.TopStores
    @GetMapping("/top-rating")
    fun getTopStores(
        @ParameterObject
        @PageableDefault(page = 0, size = 20) pageable: Pageable
    ): PageResponse<StoreResponse> {
        val storesPage = storeService.getPagedTopStores(pageable)
        return storesPage.toResponse()
    }
}