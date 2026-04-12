package org.shangahi.sellio_backend.security.service.otp

interface SmsSender {
    fun sendSms(phoneNumber: String, countryCode: String? =null, message: String)
}
