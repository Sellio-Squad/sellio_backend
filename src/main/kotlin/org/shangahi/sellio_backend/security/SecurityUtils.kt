package org.shangahi.sellio_backend.security

import org.shangahi.sellio_backend.entity.User
import org.springframework.security.authentication.AnonymousAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import java.util.UUID

object SecurityUtils {
    fun getCurrentUserId(): UUID? {
        val authentication = SecurityContextHolder.getContext().authentication

        if (authentication == null ||
            !authentication.isAuthenticated ||
            authentication is AnonymousAuthenticationToken ||
            authentication.principal == "anonymousUser"
        ) {
            return null
        }

        val principal = authentication.principal

        return when (principal) {
            is UUID -> principal
            is User -> principal.id
            is String -> try {
                UUID.fromString(principal)
            } catch (e: Exception) {
                null
            }
            else -> null
        }
    }
}