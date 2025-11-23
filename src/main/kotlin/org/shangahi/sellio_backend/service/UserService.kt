package org.shangahi.sellio_backend.service

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.UserEmailAlreadyExistsException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    private val userRepository: UserRepository,
    private val storageService: StorageService,
    @param:Lazy private val authService: AuthenticationService
) {

    fun findUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findByPhoneNumberAndIsDeletedFalse(phoneNumber)
    }

    fun findDeletedUserByPhoneNumber(phoneNumber: String): User? {
        return userRepository.findByPhoneNumberAndIsDeletedTrue(phoneNumber)
    }


    fun findById(userId: UUID): User {
        return userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()
    }

    fun createUser(request: User): User {
        if (userRepository.existsByPhoneNumberAndIsDeletedFalse(request.phoneNumber)) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        if (request.email != null && userRepository.existsByEmailAndIsDeletedFalse(request.email)) {
            throw UserEmailAlreadyExistsException()
        }
        return userRepository.save(request)
    }

    fun saveUser(user: User): User {
        return userRepository.save(user)
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

        val existingUser = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()

        if (
            request.phoneNumber != null &&
            request.phoneNumber != existingUser.phoneNumber &&
            userRepository.existsByPhoneNumberAndIsDeletedFalse(request.phoneNumber)
        ) {
            throw UserPhoneNumberAlreadyExistsException()
        }
        if (
            request.email != null &&
            request.email != existingUser.email &&
            userRepository.existsByEmailAndIsDeletedFalse(request.email)
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

    @Transactional
    fun deleteUser(userId: UUID) {
        val user = userRepository.findByIdAndIsDeletedFalse(userId)
            ?: throw UserNotFoundException()

        val updatedUser = user.copy(isDeleted = true, deletedAt = LocalDateTime.now())
        authService.logout(userId)
        userRepository.save(updatedUser)
    }

}