package org.shangahi.sellio_backend.security.service

import com.google.i18n.phonenumbers.NumberParseException
import com.google.i18n.phonenumbers.PhoneNumberUtil
import org.shangahi.sellio_backend.service.exception.InvalidPhoneNumberException
import org.springframework.stereotype.Service

@Service
class PhoneNumberValidatorService {

    private val phoneUtil = PhoneNumberUtil.getInstance()

    fun validate(phone: String, region: String = "IQ"): String {
        val parsedNumber = try {
            phoneUtil.parse(phone, region.uppercase())
        } catch (e: NumberParseException) {
            throw InvalidPhoneNumberException()
        }

        if (!phoneUtil.isValidNumber(parsedNumber)) {
            throw InvalidPhoneNumberException()
        }

        val type = phoneUtil.getNumberType(parsedNumber)
        if (type != PhoneNumberUtil.PhoneNumberType.MOBILE) {
            throw InvalidPhoneNumberException()
        }

        return phoneUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
    }

    fun extractDetails(phone: String, region: String = "IQ"): Map<String, String> {
        val parsedNumber = try {
            phoneUtil.parse(phone, region.uppercase())
        } catch (e: NumberParseException) {
            throw NumberParseException(e.errorType, e.message)
        }

        val formatted = phoneUtil.format(parsedNumber, PhoneNumberUtil.PhoneNumberFormat.E164)
        val regionCode = phoneUtil.getRegionCodeForNumber(parsedNumber)
        val countryCode = parsedNumber.countryCode.toString()
        val carrierPrefix = parsedNumber.nationalNumber.toString().take(3)

        return mapOf(
            "formatted" to formatted,
            "region" to regionCode,
            "countryCode" to countryCode,
            "carrierPrefix" to carrierPrefix
        )
    }
}
