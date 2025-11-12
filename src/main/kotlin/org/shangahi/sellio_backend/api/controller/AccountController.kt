package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.*
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.api.mapper.toRegisterUserModel
import org.shangahi.sellio_backend.service.AuthenticationService
import org.shangahi.sellio_backend.service.RegisterService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/auth")
class AccountController(
    private val authenticationService: AuthenticationService,
    private val registerService: RegisterService
) {

    @PostMapping("/login")
    fun login(
        @RequestBody request: LoginRequest
    ): AuthResponse {
        return authenticationService.login(request.phoneNumber, request.password)
    }

    @PostMapping("/create")
    fun create(
        @RequestBody user: CreateUserRequest
    ): AuthResponse {
        return registerService.createUser(user.toRegisterUserModel())
    }

    @PostMapping("/create/request-otp")
    fun requestRegisterOtp(
        @RequestBody request: RequestOtpRequest
    ): ResponseEntity<OtpRequestResponse> {
        val response = registerService.requestOtp(
            phoneNumber = request.phoneNumber,
            defaultRegion = request.defaultRegion
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/create/verify-otp")
    fun verifyRegisterOtp(
        @RequestBody request: VerifyOtpRequest
    ): ResponseEntity<Unit> {
        val response = registerService.verifyOtp(
            otp = request.otp,
            sessionId = request.sessionId
        )
        return ResponseEntity.ok(response)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(
        @RequestBody request: RefreshTokenRequest
    ): AuthResponse {
        return authenticationService.refreshToken(request.refreshToken)
    }
}
