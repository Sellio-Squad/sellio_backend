package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.request.*
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.service.ForgotPasswordService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/auth/forgot-password")
class ForgotPasswordController(
    private val forgotPasswordService: ForgotPasswordService
) {

    @PostMapping("/request")
    fun requestReset(@RequestBody request: RequestOtpRequest): OtpRequestResponse {
        return forgotPasswordService.requestReset(
            phoneNumber = request.phoneNumber,
            region = request.defaultRegion
        )
    }

    @PostMapping("/verify")
    fun verifyOtp(@RequestBody request: VerifyOtpRequest) {
        forgotPasswordService.verifyOtp(request.sessionId, request.otp)
    }

    @PostMapping("/reset")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) {
        forgotPasswordService.resetPassword(
            sessionId = request.sessionId,
            newPassword = request.newPassword,
            confirmPassword = request.confirmPassword
        )
    }
}
