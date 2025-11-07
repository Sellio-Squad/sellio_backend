package org.shangahi.sellio_backend.service

import jakarta.transaction.Transactional
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.util.UUID

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

    fun userExists(userId: UUID): Boolean {
        return userRepository.existsById(userId)
    }
    fun insertUser(user: User): User {
        if (userRepository.findByPhoneNumber(user.phoneNumber) != null) {
            throw UserPhoneNumberAlreadyExistsException()
        }
        return userRepository.save(user)
    }

    @Transactional
    fun uploadUserAvatar(userId: UUID, file: MultipartFile): User {
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



    fun updateUser(user: User): User {
        val existingUser = findById(user.id!!)
        val updatedUser = existingUser.copy(
            firstName = user.firstName,
            lastName = user.lastName,
            phoneNumber = user.phoneNumber,
            email = user.email,
            city = user.city,
            country = user.country,
            password = user.password,
            avatarUrl = user.avatarUrl
        )
        return userRepository.save(updatedUser)
    }
}