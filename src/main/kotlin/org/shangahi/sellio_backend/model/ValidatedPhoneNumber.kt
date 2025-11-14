package org.shangahi.sellio_backend.model

data class ValidatedPhoneNumber(
    val phoneNumber: String,
    val regionCode: String,
    val countryCode: String,
    val carrierPrefix: String
)