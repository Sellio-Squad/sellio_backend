package org.shangahi.sellio_backend.service

import org.hibernate.query.Page.page
import org.shangahi.sellio_backend.entity.Store
import org.shangahi.sellio_backend.repository.StoreRatingRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.nio.file.Files.size

@Service
class StoreService(
    private val storeRatingRepository: StoreRatingRepository
) {
    fun getTopStores(limit: Int = 10): Page<Store> {
        val pageable = PageRequest.of(0, limit)
        return storeRatingRepository.findTopStoresByHighestRating(pageable)
    }
    fun getPagedTopStores(pageable: Pageable): Page<Store> {
        val pageable = PageRequest.of(pageable.pageNumber, pageable.pageSize)
        return storeRatingRepository.findTopStoresByHighestRating(pageable)
    }
}
