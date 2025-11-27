package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.StoreContactCreateRequest
import org.shangahi.sellio_backend.api.dto.response.StoreContactResponse
import org.shangahi.sellio_backend.api.mapper.toRequest
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.StoreContactService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/v1/store-contacts/{storeId}")
class StoreContactController(
    private val storeContactService: StoreContactService
) {

    @PostMapping(name = "/add-contact")
    fun addContact(
        @PathVariable storeId: UUID,
        @RequestBody request: StoreContactCreateRequest
    ): StoreContactResponse {

        val storeContactRequest = request.toRequest()
        val response = storeContactService.addContact(storeId, storeContactRequest)
        return response.toResponse()
    }

    @PostMapping("/add-multiple-contacts")
    fun addMultipleContacts(
        @PathVariable storeId: UUID,
        @RequestBody requests: List<StoreContactCreateRequest>
    ): List<StoreContactResponse> {
        val storeContactRequests = requests.map { it.toRequest() }
        val response = storeContactService.addMultipleContacts(storeId, storeContactRequests)
        return response.map { it.toResponse() }
    }

    @GetMapping(name = "/get-contacts")
    fun getContacts(
        @PathVariable storeId: UUID
    ): List<StoreContactResponse> {
        val response = storeContactService.getStoreContacts(storeId)
        return response.map { it.toResponse() }
    }


    @DeleteMapping("/{contactId}")
    fun deleteContact(
        @PathVariable storeId: UUID,
        @PathVariable contactId: UUID
    ): String {
        storeContactService.deleteContact(contactId)
        return "Contact deleted"
    }
}
