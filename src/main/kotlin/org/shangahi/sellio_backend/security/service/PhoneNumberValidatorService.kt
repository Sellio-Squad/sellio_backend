package org.shangahi.sellio_backend.security.service

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.shangahi.sellio_backend.model.ValidatedPhoneNumber
import org.shangahi.sellio_backend.service.exception.InvalidPhoneNumberException
import org.shangahi.sellio_backend.service.exception.InvalidPhoneNumberRegionException
import org.springframework.stereotype.Service

@Service
class PhoneNumberValidatorService {

    private val phoneUtil = PhoneNumberUtil.getInstance()

    fun validate(phone: String, region: String?): ValidatedPhoneNumber {
        val normalized = phone.trim()

        if (normalized.isEmpty()) throw InvalidPhoneNumberException()

        val parsed = try {
            if (region.isNullOrBlank()) {
               if (!normalized.startsWith("+")) {
                    throw InvalidPhoneNumberRegionException()
                }
                phoneUtil.parse(normalized, null)
            } else {
                phoneUtil.parse(normalized, region.uppercase())
            }
        } catch (e: NumberParseException) {
            throw InvalidPhoneNumberException()
        }

        if (!phoneUtil.isValidNumber(parsed)) throw InvalidPhoneNumberException()

        val type = phoneUtil.getNumberType(parsed)
        if (type != PhoneNumberUtil.PhoneNumberType.MOBILE &&
            type != PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE) {
            throw InvalidPhoneNumberException()
        }

        val e164 = phoneUtil.format(parsed, PhoneNumberUtil.PhoneNumberFormat.E164)
        val countryCode = parsed.countryCode.toString()
        val regionCode = phoneUtil.getRegionCodeForNumber(parsed)
            ?: region?.uppercase()
            ?: throw InvalidPhoneNumberRegionException()

        val national = parsed.nationalNumber.toString()
        val carrierPrefix = national.take(3)

        return ValidatedPhoneNumber(
            phoneNumber = e164,
            regionCode = regionCode,
            countryCode = countryCode,
            carrierPrefix = carrierPrefix
        )
    }
}
