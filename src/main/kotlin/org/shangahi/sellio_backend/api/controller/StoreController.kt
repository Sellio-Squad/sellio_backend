package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.dto.StoreResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.StoreService
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stores")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/top")
    fun getTopStores(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): PageResponse<StoreResponse> {
        val pageable = PageRequest.of(page, size)
        val storesPage = storeService.getPagedTopStores(pageable)
        return storesPage.toResponse()
    }
}


