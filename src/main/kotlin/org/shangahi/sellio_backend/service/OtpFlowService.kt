package org.shangahi.sellio_backend.service

import org.shangahi.sellio_backend.service.exception.OtpExpiredException
import org.shangahi.sellio_backend.service.exception.OtpMismatchException
import org.springframework.stereotype.Service
import java.util.*

@Service
class OtpFlowService(
    private val otpSessionService: OtpSessionService,
    private val otpService: OtpService,
    private val otpAbuseService: OtpAbuseService
) {

    fun verifyOtpForSession(sessionId: String, otp: String) {
        val uuid = UUID.fromString(sessionId)
        val otpSession = otpSessionService.getOtpSession(uuid)
        val abuse = otpAbuseService.create(otpSession.phoneNumber)

        otpSessionService.validateActive(otpSession)
        otpAbuseService.ensureNotBlocked(abuse)

        try {
            otpService.verifyOtp(uuid, otp)
        } catch (e: OtpMismatchException) {
            otpAbuseService.onOtpMismatch(abuse)
            throw e
        } catch (e: OtpExpiredException) {
            otpAbuseService.onOtpMismatch(abuse)
            throw e
        }
        otpSessionService.markVerified(otpSession)
        otpAbuseService.onOtpSuccess(otpSession.phoneNumber)
    }
}
