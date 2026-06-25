package org.shangahi.sellio_backend.service.exception

internal object ErrorCode {
    //GEN_xxx
    const val GEN_VALIDATION_ERROR = "GEN_001"
    const val GEN_REQUEST_BODY_ERROR = "GEN_002"
    const val GEN_INTERNAL_SERVER_ERROR = "GEN_003"
    //USER_xxx
    const val USER_EMAIL_ALREADY_EXISTS = "USER_001"
    const val USER_PHONE_NUMBER_ALREADY_EXISTS = "USER_002"
    const val USER_COUNTY_NOT_SUPPORTED = "USER_003"
    const val USER_NOT_FOUND = "USER_004"
    //STORE_xxx
    const val STORE_EMAIL_ALREADY_EXISTS = "STORE_001"
    const val STORE_PHONE_NUMBER_ALREADY_EXIST = "STORE_002"
    const val STORE_NAME_ALREADY_EXISTS = "STORE_003"
    const val STORE_NOT_FOUND = "STORE_004"
    const val STORE_INACTIVE = "STORE_005"
    const val STORE_NOT_OWNER = "STORE_006"
    const val STORE_ALREADY_FAVORITE = "STORE_007"

    //PROD_xxx
    const val PROD_OUT_OF_STOCK = "PROD_001"
    const val PROD_NOT_ENOUGH_STOCK = "PROD_002"
    const val PROD_TITLE_ALREADY_EXISTS = "PROD_003"
    const val PROD_NOT_FOUND = "PROD_004"
    const val PROD_SAVING = "PROD_005"
    const val PROD_BASE = "PROD_006"

    //ITEM_xxx
    const val ITEM_OUT_OF_STOCK = "ITEM_001"
    const val ITEM_NOT_ENOUGH_STOCK = "ITEM_002"
    const val ITEM_IN_USE = "ITEM_003"
    const val ITEM_NOT_FOUND = "ITEM_004"
    const val ITEM_SAVING = "ITEM_005"
    const val ITEM_COLOR = "ITEM_006"
    const val ITEM_SIZE = "ITEM_007"

    //CATEG_xxx
    const val CATEG_ALREADY_EXISTS = "CATEG_001"
    const val CATEG_NOT_FOUND = "CATEG_004"

    //SUBCATEG_xxx
    const val SUBCATEG_ALREADY_EXISTS = "CATEG_001"
    const val SUBCATEG_NOT_FOUND = "CATEG_004"

    //IMAGE_xxx
    const val IMAGE_INVALID_FORMAT = "IMAGE_001"
    const val IMAGE_UPLOAD_FAILED = "IMAGE_002"

    //AUTH_xxx
    const val AUTH_INVALID_CREDENTIALS = "AUTH_001"
    const val AUTH_INVALID_REFRESH_TOKEN = "AUTH_002"
    const val AUTH_UNAUTHORIZED = "AUTH_003"
    const val AUTH_SESSION_ID_NOT_FOUND = "AUTH_004"
    const val AUTH_PASSWORD_NOT_MATCH = "AUTH_005"
    const val AUTH_USER_REGISTRATION_PENDING = "AUTH_006"
    const val SESSION_EXPIRED = "AUTH_007"
    const val AUTH_USER_ROLE = "AUTH_008"

    //PHONE_xxx
    const val INVALID_PHONE_NUMBER = "PHONE_001"
    const val INVALID_PHONE_NUMBER_REGION = "PHONE_002"

    //OTP_xxx
    const val OTP_EXPIRED = "OTP_001"
    const val OTP_INVALID = "OTP_002"
    const val OTP_MISMATCH = "OTP_003"
    const val OTP_BLOCKED = "OTP_004"

    //STORE_CONTACT_xxx
    const val STORE_CONTACT_TYPE_ALREADY_EXISTS = "STORE_CONTACT_001"
    const val STORE_CONTACT_NOT_FOUND = "STORE_CONTACT_003"
    const val STORE_CONTACT_TYPE_DUPLICATE = "STORE_CONTACT_004"

    //COUNTRY_CITY_xxx
    const val CITIES_NOT_FOUND = "COUNTRY_CITY_001"

    //CATEG_SECTION_xxx
    const val CATEG_SECTION_ALREADY_EXISTS = "CATEG_SECTION_001"
    const val CATEG_SECTION_NOT_FOUND = "CATEG_SECTION_002"

    //RATING_xxx
    const val RATING_NOT_PURCHASED = "RATING_001"
    const val RATING_NOT_FOUND = "RATING_002"
    const val RATING_NOT_OWNER = "RATING_003"
}