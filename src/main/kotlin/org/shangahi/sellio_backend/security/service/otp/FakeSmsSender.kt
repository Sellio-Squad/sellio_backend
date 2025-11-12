package org.shangahi.sellio_backend.security.service.otp

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("!prod")
class FakeSmsSender : SmsSender {
    override fun sendSms(phoneNumber: String, countryCode: String, message: String) {
    }
}