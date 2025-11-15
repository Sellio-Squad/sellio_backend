package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.service.exception.PasswordNotMatchException
import org.shangahi.sellio_backend.service.exception.UnauthorizedException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class ResetPasswordService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder
) {

    fun resetPassword(
        userId: UUID,
        currentPassword: String,
        newPassword: String,
        confirmPassword: String
    ) {
        if (newPassword != confirmPassword) {
            throw PasswordNotMatchException()
        }

        val user = userService.findById(userId)

        if (!passwordEncoder.matches(currentPassword, user.password)) {
            throw UnauthorizedException()
        }

        val updatedUser = user.copy(password = passwordEncoder.encode(newPassword))
        userService.saveUser(updatedUser)
    }
}
