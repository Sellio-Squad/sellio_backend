package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.CreateStoreRequest
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.api.dto.response.StoreDetailsResponse
import org.shangahi.sellio_backend.api.dto.response.StoreResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.StoreService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.*

// GET http://localhost:8080/v1/stores/store_id
// POST http://localhost:8080/v1/stores
@RestController
@RequestMapping("/v1/stores")
class StoreController(
    private val storeService: StoreService
) {
    @PostMapping
    fun addStore(@RequestBody request: CreateStoreRequest): ResponseEntity<StoreCreationResponse> {

        val response = storeService.createStore(request)

        val location = URI.create("/v1/stores/${response.id}")

        return ResponseEntity
            .created(location)
            .body(response)
    }


    @GetMapping("/{storeId}")
    fun getStoreDetailsById(@PathVariable storeId: UUID): StoreDetailsResponse {
        return storeService.getStoreDetailsById(storeId)
    }

    @GetMapping("/top")
    fun getTopStores(
        pageable: Pageable
    ): PageResponse<StoreResponse> {
        val storesPage = storeService.getPagedTopStores(pageable)
        return storesPage.toResponse()
    }
}