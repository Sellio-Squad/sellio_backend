package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.UserInsertRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.mapper.toUser
import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.UserEmailAlreadyExistsException
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
) {

    fun findById(userId: UUID): User {
        return userRepository.findByIdOrNull(userId)
            ?: throw UserNotFoundException()
    }

    fun insertUser(request: UserInsertRequest): UserInfoResponse {
        if (userRepository.existsByPhoneNumber(request.phoneNumber)) {
            throw UserPhoneNumberAlreadyExistsException()
        }

        if (request.email != null && userRepository.existsByEmail(request.email)) {
            throw UserEmailAlreadyExistsException()
        }
        val savedUser = userRepository.save(request.toUser())
        return savedUser.toResponse()
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