package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
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
    fun findAllByStoreId(storeId: UUID): List<Product>
    fun findStoreFeaturedProductsByStoreId(storeId: UUID, pageable: Pageable): Page<Product>

    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Product>

    @EntityGraph(attributePaths = ["images"])
    fun findAllByStoreId(storeId: UUID, pageable: Pageable): Page<Product>

    @Query(
        """
    SELECT DISTINCT p FROM Product p
    LEFT JOIN FETCH p.store
    LEFT JOIN FETCH p.images
    LEFT JOIN FETCH p.items
    LEFT JOIN FETCH p.productSubCategories psc
    LEFT JOIN FETCH psc.subCategory
    WHERE p.id = :id
"""
    )
    fun findByIdWithItems(@Param("id") id: UUID): Product?

    @Query(
        """
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.store
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.items
        LEFT JOIN FETCH p.productSubCategories psc
        LEFT JOIN FETCH psc.subCategory
        WHERE p.id IN :ids
    """
    )
    fun findAllByIdWithItems(@Param("ids") ids: Collection<UUID>): List<Product>

    @Query(
        """
        SELECT DISTINCT p FROM Product p
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.items
        LEFT JOIN FETCH p.productSubCategories
        WHERE p.isUsed = true
    """
    )
    fun findAllUsedProductsWithDetails(pageable: Pageable): Page<Product>

    @Query(
        """
        SELECT p FROM Product p
        LEFT JOIN FETCH p.productSubCategories psc
        LEFT JOIN FETCH psc.subCategory sc
        LEFT JOIN FETCH p.images
        LEFT JOIN FETCH p.items
        WHERE p.store.id = :storeId AND sc.category.id = :categoryId
    """
    )
    fun findCustomProductsByStoreAndCategory(
        @Param("storeId") storeId: UUID,
        @Param("categoryId") categoryId: UUID,
        pageable: Pageable
    ): Page<Product>

    fun existsByTitleAndIdNot(title: String, id: UUID): Boolean
    fun existsByTitle(title: String): Boolean


    @Query(
        """
        SELECT DISTINCT p FROM Product p
        JOIN p.productSubCategories psc
        WHERE psc.subCategory.id = :subCategoryId
          AND p.store.id = :storeId
    """
    )
    fun findBySubCategoryAndStore(
        @Param("subCategoryId") subCategoryId: UUID,
        @Param("storeId") storeId: UUID,
        pageable: Pageable
    ): Page<Product>

    fun findByTitleContainingIgnoreCaseAndStoreCityIgnoreCase(
        title: String,
        city: String,
        pageable: Pageable
    ): Page<Product>

    @Query(
        """
    SELECT DISTINCT p FROM Product p 
    JOIN p.productSubCategories psc 
    JOIN psc.subCategory sc 
    WHERE sc.category.id = :categoryId
"""
    )
    fun findByCategoryId(@Param("categoryId") categoryId: UUID, pageable: Pageable): Page<Product>
    @Query("""
        SELECT DISTINCT p FROM Product p 
        JOIN p.productSubCategories psc 
        WHERE psc.subCategory.id = :subCategoryId
    """)
    fun findBySubCategoryId(
        @Param("subCategoryId") subCategoryId: UUID,
        pageable: Pageable
    ): Page<Product>
}