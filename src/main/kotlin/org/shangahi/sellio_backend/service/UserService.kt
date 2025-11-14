package org.shangahi.sellio_backend.service

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.UserEmailAlreadyExistsException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val storageService: StorageService
) {

    fun findUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findByPhoneNumber(phoneNumber)
    }

    fun findById(userId: UUID): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()
    }

    fun createUser(request: User): User {
        if (userRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        if (request.email != null && userRepository.existsByEmail(request.email)) {
            throw UserEmailAlreadyExistsException()
        }
        return userRepository.save(request)
    }

    @Transactional
    fun uploadUserAvatar(
        userId: UUID,
        file: MultipartFile
    ): User {
        val user = findById(userId)

        user.avatarUrl?.let { oldUrl ->
            runCatching { storageService.deleteImage(oldUrl) }
        }

        val imageUrl = storageService.uploadImage(
            file = file,
            fileName = user.firstName,
            folderName = "avatars"
        )

        val updatedUser = user.copy(avatarUrl = imageUrl)
        return userRepository.save(updatedUser)
    }


    fun updateUser(
        userId: UUID,
        request: UserUpdateRequest
    ): User {

        val existingUser = userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()

        if (
            request.phoneNumber != null &&
            request.phoneNumber != existingUser.phoneNumber &&
            userRepository.existsByPhoneNumber(request.phoneNumber)
        ) {
            throw UserPhoneNumberAlreadyExistsException()
        }
        if (
            request.email != null &&
            request.email != existingUser.email &&
            userRepository.existsByEmail(request.email)
        ) {
            throw UserEmailAlreadyExistsException()
        }
        val updatedUser = existingUser.copy(
            firstName = request.firstName ?: existingUser.firstName,
            lastName = request.lastName ?: existingUser.lastName,
            phoneNumber = request.phoneNumber ?: existingUser.phoneNumber,
            email = request.email ?: existingUser.email,
            city = request.city ?: existingUser.city,
            country = request.country ?: existingUser.country,
            avatarUrl = request.avatarUrl ?: existingUser.avatarUrl
        )
        return userRepository.save(updatedUser)
    }
}