package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.StoreDetailsResponse
import org.shangahi.sellio_backend.service.StoreService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

// GET http://localhost:8080/v1/stores/store_id
@RestController
@RequestMapping("/v1/stores")
class StoreController(
    private val storeService: StoreService
) {

    @GetMapping("/{storeId}")
    fun getStoreDetailsById(@PathVariable storeId: UUID): StoreDetailsResponse {
        return storeService.getStoreDetailsById(storeId)
    }
}