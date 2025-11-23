package org.shangahi.sellio_backend.security

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.shangahi.sellio_backend.security.service.JwtService
import org.shangahi.sellio_backend.service.UserService
import org.shangahi.sellio_backend.service.exception.UserNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val userService: UserService,
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authHeader = request.getHeader(AUTH_HEADER)

        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            filterChain.doFilter(request, response)
            return
        }
        val token = authHeader.substringAfter(BEARER_PREFIX).trim()

        try {
            val userId = jwtService.parseClaims(token)

            if (SecurityContextHolder.getContext().authentication == null) {
                val user = userService.findById(userId)

                val authentication = UsernamePasswordAuthenticationToken(
                    user.id,
                    null,
                    emptyList()
                )
                authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
                SecurityContextHolder.getContext().authentication = authentication
            }

            filterChain.doFilter(request, response)

        } catch (ex: ExpiredJwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("JWT token expired")
        } catch (ex: MalformedJwtException) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            response.writer.write("Invalid JWT token")
        } catch (ex: UserNotFoundException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.writer.write("User not found")
        } catch (ex: Exception) {
            response.status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR
            response.writer.write("Authentication error: ${ex.message}")
        }
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }

}