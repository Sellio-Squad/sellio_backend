package org.shangahi.sellio_backend.security.service.otp

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Service
@Profile("!prod")
class FakeOtpGeneratorService : OtpGenerator {
    override fun generateOtp(): String {
        return "9999"
    }
}
