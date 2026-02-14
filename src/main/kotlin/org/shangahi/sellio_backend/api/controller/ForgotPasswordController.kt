package org.shangahi.sellio_backend.api.controller

import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.RequestOtpRequest
import org.shangahi.sellio_backend.api.dto.request.ResetPasswordRequest
import org.shangahi.sellio_backend.api.dto.request.VerifyOtpRequest
import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.api.swagger.doc.ForgotPasswordDoc
import org.shangahi.sellio_backend.service.ForgotPasswordService
import org.shangahi.sellio_backend.service.OtpFlowService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/forgot-password")
class ForgotPasswordController(
    private val forgotPasswordService: ForgotPasswordService,
    private val otpFlowService: OtpFlowService
) {

    @PostMapping("/request")
    @ForgotPasswordDoc.RequestOtp
    fun requestReset(@RequestBody request: RequestOtpRequest): OtpResponse {
        return forgotPasswordService.requestReset(
            phoneNumber = request.phoneNumber,
            region = request.defaultRegion
        )
    }

    @PostMapping("/verify")
    @ForgotPasswordDoc.VerifyOtp
    fun verifyOtp(@RequestBody request: VerifyOtpRequest) {
        otpFlowService.verifyOtpForSession(request.sessionId, request.otp)
    }

    @PostMapping("/reset")
    @ForgotPasswordDoc.ResetPassword
    fun resetPassword(@RequestBody @Valid request: ResetPasswordRequest) {
        forgotPasswordService.resetPassword(
            sessionId = request.sessionId,
            newPassword = request.newPassword,
        )
    }
}
