package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.dto.StoreResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.StoreService
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stores")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/top")
    fun getTopStores(
        pageable: Pageable
    ): PageResponse<StoreResponse> {
        val storesPage = storeService.getPagedTopStores(pageable)
        return storesPage.toResponse()
    }
}


