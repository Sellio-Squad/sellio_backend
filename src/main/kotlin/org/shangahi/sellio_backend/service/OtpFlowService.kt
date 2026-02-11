package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.service.exception.OtpMismatchException
import org.springframework.stereotype.Service
import java.util.*

@Service
class OtpFlowService(
    private val otpSessionService: OtpSessionService,
    private val otpService: OtpService
) {

    fun verifyOtpForSession(sessionId: String, otp: String) {
        val uuid = UUID.fromString(sessionId)
        val otpSession = otpSessionService.getOtpSession(uuid)

        otpSessionService.ensureNotBlocked(otpSession)

        try {
            otpService.verifyOtp(uuid, otp)
        } catch (e: OtpMismatchException) {
            otpSessionService.onOtpMismatch(otpSession)
            throw e
        }
        otpSessionService.markVerified(otpSession)
    }
}
