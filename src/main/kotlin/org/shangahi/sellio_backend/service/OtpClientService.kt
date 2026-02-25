package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.api.dto.otp.SendOtpRequest
import org.shangahi.sellio_backend.api.dto.otp.SendOtpResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class OtpClientService(
    private val webClient: WebClient,
    @Value("\${otp.service.url}")
    private val otpServiceUrl: String
) {

    fun sendOtp(phone: String, otp: String) {

        val request = SendOtpRequest(
            phone = phone,
            message = "Your verification code is:",
            otp = otp
        )

        val response = webClient.post()
            .uri(otpServiceUrl)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(SendOtpResponse::class.java)
            .block()

        if (response?.success != true) {
            throw RuntimeException("Failed to send OTP: ${response?.message}")
        }
    }
}