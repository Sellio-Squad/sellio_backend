package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.StoreContact
import org.shangahi.sellio_backend.model.StoreContactCreationModel
import org.shangahi.sellio_backend.model.StoreContactModel
import org.shangahi.sellio_backend.repository.StoreContactRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.service.exception.StoreContactNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreContactTypeAlreadyExistsException
import org.shangahi.sellio_backend.service.exception.StoreContactTypeDuplicateException
import org.shangahi.sellio_backend.service.exception.StoreContactValueAlreadyExistsException
import org.shangahi.sellio_backend.service.exception.StoreNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class StoreContactService(
    private val storeRepository: StoreRepository,
    private val storeContactRepository: StoreContactRepository
) {

    @Transactional
    fun addContacts(storeId: UUID, requests: List<StoreContactCreationModel>)
    : List<StoreContactModel> {

        val store = storeRepository.findById(storeId)
            .orElseThrow { StoreNotFoundException() }

        val existingTypes = storeContactRepository.findAllByStoreId(storeId)
            .map { it.type }.toSet()

        val requestTypes = requests.map { it.type }

        val duplicatesInRequest = requestTypes.groupingBy { it }.eachCount().filter { it.value > 1 }
        if (duplicatesInRequest.isNotEmpty()) {
            throw StoreContactTypeDuplicateException()
        }

        val conflictingTypes = requestTypes.intersect(existingTypes)
        if (conflictingTypes.isNotEmpty()) {
            throw StoreContactTypeAlreadyExistsException()
        }

        val savedContacts = storeContactRepository.saveAll(
            requests.map {
                StoreContact(
                    type = it.type,
                    value = it.value,
                    store = store
                )
            }
        )

        return savedContacts.map {
            StoreContactModel(
                id = it.id!!,
                storeId = it.store.id!!,
                type = it.type,
                value = it.value
            )
        }
    }

    @Transactional(readOnly = true)
    fun getStoreContacts(storeId: UUID): List<StoreContactModel> {
        return storeContactRepository.findAllByStoreId(storeId)
            .map {
                StoreContactModel(
                    id = it.id!!,
                    storeId = it.store.id!!,
                    type = it.type,
                    value = it.value
                )
            }
    }

    @Transactional
    fun updateContact(
        contactId: UUID,
        model: StoreContactCreationModel
    ): StoreContact {

        val contact = storeContactRepository.findById(contactId)
            .orElseThrow { StoreContactNotFoundException() }

        val storeId = contact.store.id!!

        val isTypeChanged = model.type != contact.type
        val isValueChanged = model.value != contact.value

        if (isTypeChanged &&
            storeContactRepository.existsByTypeAndStoreId(model.type, storeId)
        ) {
            throw StoreContactTypeAlreadyExistsException()
        }

        if (isValueChanged &&
            storeContactRepository.existsByTypeAndValue(model.type, model.value)
        ) {
            throw StoreContactValueAlreadyExistsException()
        }

        val updated = contact.copy(
            type = model.type,
            value = model.value
        )

        return storeContactRepository.save(updated)
    }

    @Transactional
    fun deleteContact(contactId: UUID) {
        storeContactRepository.deleteById(contactId)
    }

}
