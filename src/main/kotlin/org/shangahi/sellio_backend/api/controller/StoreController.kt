package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.PageResponse
import org.shangahi.sellio_backend.api.dto.StoreResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.StoreService
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/stores")
class StoreController(
    private val storeService: StoreService
) {
    @GetMapping("/top-store")
    fun getTopStores(@RequestParam(defaultValue = "10") pageable: Pageable):
            ResponseEntity<PageResponse<StoreResponse>> {
        val page = storeService.getPagedTopStores(pageable)
        return ResponseEntity.ok(page.toResponse())
    }
}

