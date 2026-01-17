package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.*
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.api.swagger.doc.AccountDoc
import org.shangahi.sellio_backend.service.AccountService
import org.shangahi.sellio_backend.service.AuthenticationService
import org.shangahi.sellio_backend.service.RegisterService
import org.shangahi.sellio_backend.service.ResetPasswordService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/auth")
class AccountController(
    private val authenticationService: AuthenticationService,
    private val registerService: RegisterService,
    private val resetPasswordService: ResetPasswordService,
    private val accountService: AccountService
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
    @AccountDoc.ChangePassword
    fun resetPassword(
        @RequestBody @Valid request: ChangePasswordRequest,
        @AuthenticationPrincipal userId: UUID
    ) {
        return resetPasswordService.resetPassword(userId, request.currentPassword, request.newPassword )
    }

    @PostMapping("/logout")
    fun logout(@AuthenticationPrincipal userId: UUID) {
        authenticationService.logout(userId)
    }

    @PostMapping("/change-phone/initiate")
    @Operation(summary = "Request OTP for new phone number", security = [SecurityRequirement(name = "bearer-key")])
    fun initiateChangePhone(
        @RequestBody @Valid request: ChangePhoneRequest,
        @AuthenticationPrincipal userId: UUID
    ): ResponseEntity<OtpRequestResponse> {
        val response = accountService.initiatePhoneNumberChange(userId, request)
        return ResponseEntity.ok(response)
    }

    @PostMapping("/change-phone/verify")
    @Operation(summary = "Verify OTP and update phone number", security = [SecurityRequirement(name = "bearer-key")])
    fun verifyChangePhone(
        @RequestBody @Valid request: VerifyOtpRequest,
        @AuthenticationPrincipal userId: UUID
    ): ResponseEntity<String> {
        accountService.verifyAndChangePhoneNumber(userId, request.sessionId, request.otp)
        return ResponseEntity.ok("Phone number updated successfully")
    }


}
