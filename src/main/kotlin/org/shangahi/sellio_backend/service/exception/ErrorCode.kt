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
    const val PROD_NOT_FOUND = "PROD_004"
    const val PROD_SAVING = "PROD_005"

    //ITEM_xxx
    const val ITEM_OUT_OF_STOCK = "ITEM_001"
    const val ITEM_NOT_ENOUGH_STOCK = "ITEM_002"
    const val ITEM_NOT_FOUND = "ITEM_004"
    const val ITEM_SAVING = "ITEM_005"
    const val ITEM_COLOR = "ITEM_006"
    const val ITEM_SIZE = "ITEM_007"
    const val ITEM_WEIGHT = "ITEM_008"

    //CATEG_xxx
    const val CATEG_ALREADY_EXISTS = "CATEG_001"
    const val CATEG_NOT_FOUND = "CATEG_004"

    //SUBCATEG_xxx
    const val SUBCATEG_ALREADY_EXISTS = "CATEG_001"
    const val SUBCATEG_NOT_FOUND = "CATEG_004"

    //IMAGE_xxx
    const val IMAGE_INVALID_FORMAT = "IMAGE_001"
    const val IMAGE_UPLOAD_FAILED = "IMAGE_002"
}