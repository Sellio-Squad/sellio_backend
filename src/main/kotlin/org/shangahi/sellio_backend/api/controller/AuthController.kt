package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.LoginRequest
import org.shangahi.sellio_backend.api.dto.request.RefreshTokenRequest
import org.shangahi.sellio_backend.api.dto.request.RegisterRequest
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.mapper.toRegisterUserModel
import org.shangahi.sellio_backend.service.AuthenticationService
import org.shangahi.sellio_backend.service.RegisterService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AuthController(
    private val authenticationService: AuthenticationService,
    private val registerService: RegisterService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): AuthResponse {
        return authenticationService.login(request.phoneNumber, request.password)
    }

    @PostMapping("/register")
    fun register(
        @RequestBody user: RegisterRequest
    ): AuthResponse {
        return registerService.register(user.toRegisterUserModel())
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody request: RefreshTokenRequest
    ): AuthResponse {
        return authenticationService.refreshToken(request.refreshToken)
    }
}
