package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ProductRepository : JpaRepository<Product, UUID> {

    @Query(
        """
        SELECT p FROM Product p 
        LEFT JOIN FETCH p.images 
        WHERE p.store.id = :storeId AND p.isFeatured = true
    """
    )
    fun findStoreFeaturedProductsByStoreId(storeId: UUID, pageable: Pageable): Page<Product>

    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Product>

    @EntityGraph(attributePaths = ["images"])
    fun findAllByStoreId(storeId: UUID, pageable: Pageable): Page<Product>
}