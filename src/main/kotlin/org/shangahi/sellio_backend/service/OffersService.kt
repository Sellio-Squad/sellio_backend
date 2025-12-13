package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.request.OfferRequest
import org.shangahi.sellio_backend.entity.BannerActionType
import org.shangahi.sellio_backend.entity.Offer
import org.shangahi.sellio_backend.repository.OffersRepository
import org.shangahi.sellio_backend.service.exception.SellioException
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.Instant

@Service
class OffersService(
    private val offersRepository: OffersRepository,
    private val storageService: StorageService,
) {
    fun getOffers(): List<Offer> {
        return offersRepository.findAllActiveOffers()
    }

    fun createOffer(imageFile: MultipartFile, request: OfferRequest): Offer {
        val imageUrl = storageService.uploadImage(
            imageFile,
            fileName = request.title ?: "${request.actionType} ${Instant.now()}",
            folderName = "Offers"
        )

        val newOffer = Offer(
            imageUrl = imageUrl,
            title = request.title,
            actionType = try {
                BannerActionType.valueOf(request.actionType.uppercase())
            } catch (e: SellioException) {
                BannerActionType.NONE
            },
            actionId = request.actionId,
            startDate = request.startDate?.let { Instant.ofEpochMilli(it) },
            endDate = request.endDate?.let { Instant.ofEpochMilli(it) },
            priority = request.priority,
            isActive = true
        )

        return offersRepository.save(newOffer)
    }
}
