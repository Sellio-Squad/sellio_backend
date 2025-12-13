package org.shangahi.sellio_backend.repository

import org.shangahi.sellio_backend.entity.Offer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.Instant

@Repository
interface OffersRepository : JpaRepository<Offer, Long> {

    @Query(
        """
        SELECT o FROM Offer o 
        WHERE o.isActive = true 
        AND (o.startDate IS NULL OR o.startDate <= CURRENT_TIMESTAMP)
        AND (o.endDate IS NULL OR o.endDate >= CURRENT_TIMESTAMP)
        ORDER BY o.priority ASC
    """
    )
    fun findAllActiveOffers(): List<Offer>

   @Query(
       """
           SELECT o FROM Offer o
           WHERE o.isActive = true
           AND o.endDate < :endDate
       """
   )
    fun findAllByIsActiveTrueAndEndDateBefore(endDate: Instant): List<Offer>

}