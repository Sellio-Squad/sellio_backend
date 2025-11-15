package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.ResetPasswordRequest
import org.shangahi.sellio_backend.api.dto.request.CreateUserRequest
import org.shangahi.sellio_backend.api.dto.request.LoginRequest
import org.shangahi.sellio_backend.api.dto.request.RefreshTokenRequest
import org.shangahi.sellio_backend.api.dto.request.VerifyOtpRequest
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.api.swagger.doc.AccountDoc
import org.shangahi.sellio_backend.service.AuthenticationService
import org.shangahi.sellio_backend.service.RegisterService
import org.shangahi.sellio_backend.service.ResetPasswordService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/v1/auth")
class AccountController(
    private val authenticationService: AuthenticationService,
    private val registerService: RegisterService,
    private val resetPasswordService: ResetPasswordService
) {

    @PostMapping("/login")
    @AccountDoc.Login
    fun login(
        @RequestBody request: LoginRequest
    ): AuthResponse {
        return authenticationService.login(request.phoneNumber, request.password)
    }

    @PostMapping("/create")
    @AccountDoc.CreateUser
    fun create(@RequestBody request: CreateUserRequest): ResponseEntity<OtpRequestResponse> {
        val response = registerService.prepareRegistration(request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/create/verify-otp")
    @AccountDoc.VerifyOtp
    fun verifyOtp(@RequestBody request: VerifyOtpRequest): AuthResponse {
        return registerService.verifyOtpAndCreateUser(request.sessionId, request.otp)
    }

    @PostMapping("/refresh-token")
    @AccountDoc.RefreshToken
    fun refreshToken(
        @RequestBody request: RefreshTokenRequest
    ): AuthResponse {
        return authenticationService.refreshToken(request.refreshToken)
    }

    @PostMapping("/reset-password")
    fun resetPassword(
        @RequestBody request: ResetPasswordRequest,
        @AuthenticationPrincipal userId: UUID
    ) {
        return resetPasswordService.resetPassword(userId, request.currentPassword, request.newPassword, request.confirmPassword)
    }
}
