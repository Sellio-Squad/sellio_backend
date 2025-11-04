package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.entity.User
import org.shangahi.sellio_backend.repository.UserRepository
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.shangahi.sellio_backend.service.exception.UserPhoneNumberAlreadyExistsException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class UserService(
    private val userRepository: UserRepository,
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



}