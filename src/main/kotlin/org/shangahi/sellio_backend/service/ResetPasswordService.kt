package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.service.exception.CurrentPasswordIncorrectException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class ResetPasswordService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun resetPassword(
        userId: UUID,
        currentPassword: String,
        newPassword: String,
    ) {

        val user = userService.findById(userId)

        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw CurrentPasswordIncorrectException()
        }

        val updatedUser = user.copy(password = passwordEncoder.encode(newPassword))
        userService.saveUser(updatedUser)
    }
}
