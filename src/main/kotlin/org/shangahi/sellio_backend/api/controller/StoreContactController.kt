package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.StoreContactCreateRequest
import org.shangahi.sellio_backend.api.dto.response.StoreContactResponse
import org.shangahi.sellio_backend.api.mapper.toRequest
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.mapper.toStoreContactResponse
import org.shangahi.sellio_backend.service.StoreContactService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/store-contacts/")
class StoreContactController(
    private val storeContactService: StoreContactService
) {

    @PostMapping("{storeId}/add-contacts")
    fun addContacts(
        @PathVariable storeId: UUID,
        @RequestBody requests: List<StoreContactCreateRequest>
    ): List<StoreContactResponse> {
        val storeContactRequests = requests.map { it.toRequest() }
        val response = storeContactService.addContacts(storeId, storeContactRequests)
        return response.map { it.toResponse() }
    }

    @GetMapping( "{storeId}/get-contacts")
    fun getContacts(
        @PathVariable storeId: UUID
    ): List<StoreContactResponse> {
        val response = storeContactService.getStoreContacts(storeId)
        return response.map { it.toResponse() }
    }

    @PutMapping("update-contact/{contactId}")
    fun updateContact(
        @PathVariable contactId: UUID,
        @RequestBody request: StoreContactCreateRequest
    ): StoreContactResponse {
        val storeContactRequest = request.toRequest()
        val response = storeContactService.updateContact(contactId, storeContactRequest)
        return response.toStoreContactResponse()
    }

    @DeleteMapping("delete-contact/{contactId}")
    fun deleteContact(
        @PathVariable contactId: UUID
    ): String {
        storeContactService.deleteContact(contactId)
        return "Contact deleted"
    }
}
