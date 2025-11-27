package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.StoreContact
import org.shangahi.sellio_backend.model.ContactType
import org.shangahi.sellio_backend.model.StoreContactCreationModel
import org.shangahi.sellio_backend.model.StoreContactModel
import org.shangahi.sellio_backend.repository.StoreContactRepository
import org.shangahi.sellio_backend.repository.StoreRepository
import org.shangahi.sellio_backend.service.exception.StoreContactNotFoundException
import org.shangahi.sellio_backend.service.exception.StoreContactTypeAlreadyExistsException
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
    fun addContact(
        storeId: UUID,
        request: StoreContactCreationModel
    ): StoreContactModel {

        val store = storeRepository.findById(storeId)
            .orElseThrow { StoreNotFoundException() }


        if (storeContactRepository.existsByTypeAndStoreId(request.type, storeId)) {
            throw StoreContactTypeAlreadyExistsException()
        }

        if (storeContactRepository.existsByTypeAndValue(request.type, request.value)) {
            throw StoreContactValueAlreadyExistsException()
        }

        val saved = storeContactRepository.save(
            StoreContact(
                type = request.type,
                value = request.value,
                store = store
            )
        )

        return StoreContactModel(
            id = saved.id!!,
            storeId = saved.store.id!!,
            type = saved.type,
            value = saved.value
        )
    }

    @Transactional
    fun addMultipleContacts(storeId: UUID, requests: List<StoreContactCreationModel>): List<StoreContactModel> {

        val store = storeRepository.findById(storeId)
            .orElseThrow { StoreNotFoundException() }


        val existingTypes = storeContactRepository.findAllByStoreId(storeId).map { it.type }.toSet()
        requests.forEach {
            existingTypes.contains(it.type).let {
                throw StoreContactNotFoundException()
            }
        }

        requests.forEach {
            storeContactRepository.existsByTypeAndValue(it.type, it.value).let {
                throw StoreContactValueAlreadyExistsException()
            }
        }

        val savedContacts = storeContactRepository.saveAll(
            requests.map {
                StoreContact(type = it.type, value = it.value, store = store)
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
    fun deleteContact(contactId: UUID) {
        storeContactRepository.deleteById(contactId)
    }

}
