package org.shangahi.sellio_backend.api.swagger

object ErrorResponseExample {

    //region General
    const val VALIDATION_ERROR = """
    {
    "timestamp": "2025-11-07T13:42:51.484853700Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Validation failed for 1 field(s)",
    "path": "/v1/user/e8387af4-0646-403a-9bfb-b718252ce48b/update",
    "code": "GEN_001",
    "validationErrors": {
        "email": "Invalid email format"
        }
    }
    """

    const val REQUEST_BODY_ERROR = """
    {
    "timestamp": "2025-11-07T12:39:23.638971600Z",
    "status": 400,
    "error": "Bad Request",
    "message": "Required field 'field name' is missing.",
    "path": "/v1/user/insert",
    "code": "GEN_002",
    "validationErrors": null
}
"""

    const val INTERNAL_SERVER_ERROR = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 500,
      "error": "Internal Server Error",
      "message": "An unexpected internal error occurred",
      "path": "/v1/<resource>",
      "code": "GEN_003"
    }
"""
//endregion

    //region User
    const val USER_EMAIL_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "This email is already registered",
      "path": "/v1/users",
      "code": "USER_001"
    }
"""

    const val USER_PHONE_NUMBER_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "This phone number is already registered",
      "path": "/v1/users",
      "code": "USER_002"
    }
"""

    const val USER_COUNTY_NOT_SUPPORTED = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 400,
      "error": "Bad Request",
      "message": "Sorry, we do not support provided country at the moment",
      "path": "/v1/users",
      "code": "USER_003"
    }
"""

    const val USER_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "User with provided ID was not found",
      "path": "/v1/users",
      "code": "USER_004"
    }
"""
//endregion

    //region Store
    const val STORE_EMAIL_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "Store email already exists, you should try another one",
      "path": "/v1/stores",
      "code": "STORE_001",
      "validationErrors": null
    }
"""

    const val STORE_PHONE_NUMBER_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "Store phone number already exists, you should try another one",
      "path": "/v1/stores",
      "code": "STORE_002",
      "validationErrors": null
    }
"""

    const val STORE_TITLE_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "Please try different name, Store name already exists",
      "path": "/v1/stores",
      "code": "STORE_003",
      "validationErrors": null
    }
"""

    const val STORE_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but store not found",
      "path": "/v1/stores",
      "code": "STORE_004",
      "validationErrors": null
    }
"""

    const val STORE_INACTIVE = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 400,
      "error": "Bad Request",
      "message": "Unfortunately this store is inactive",
      "path": "/v1/stores",
      "code": "STORE_005"
    }
"""

    const val STORE_NOT_OWNER = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 403,
      "error": "Forbidden",
      "message": "this owner ID already has a store",
      "path": "/v1/stores",
      "code": "STORE_006"
      "validationErrors": null
    }
"""

    const val STORE_ALREADY_FAVORITE = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "Store already favorited by this user",
      "path": "/v1/stores/favorites",
      "code": "STORE_007"
    }
"""
//endregion

    //region Product
    const val PROD_OUT_OF_STOCK = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 400,
      "error": "Bad Request",
      "message": "This product is currently out of stock",
      "path": "/v1/products",
      "code": "PROD_001"
    }
"""

    const val PROD_NOT_ENOUGH_STOCK = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 400,
      "error": "Bad Request",
      "message": "Not enough stock. You requested 10, but only 5 are available",
      "path": "/v1/products",
      "code": "PROD_002"
    }
"""

    const val PROD_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but this product was not found",
      "path": "/v1/products",
      "code": "PROD_004"
    }
"""
//endregion

    //region Category
    const val CATEG_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "This Category already exists",
      "path": "/v1/categories",
      "code": "CATEG_001"
    }
"""

    const val CATEG_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but this Category was not found",
      "path": "/v1/categories",
      "code": "CATEG_004"
    }
"""

//endregion

//region Subcategory

    const val SUBCATEG_ALREADY_EXISTS = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 409,
      "error": "Conflict",
      "message": "This Subcategory already exists",
      "path": "/v1/subcategories",
      "code": "CATEG_001"
    }
"""

    const val SUBCATEG_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but this Subcategory was not found",
      "path": "/v1/subcategories",
      "code": "CATEG_004"
    }
"""
//endregion


    //region Color
    const val COLOR_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but this Color was not found",
      "path": "/v1/size",
      "code": "ITEM_006"
    }
    """

    const val COLOR_ALREADY_EXISTS = """
    {
    "timestamp": "2025-11-14T13:35:31.382915600Z",
    "status": 409,
    "error": "Conflict",
    "message": "Color value already exists",
    "path": "/v1/size",
    "code": "ITEM_006",
    "validationErrors": null
    }
    """
//endregion


    //region Size
    const val SIZE_NOT_FOUND = """
    {
      "timestamp": "2025-11-05T21:50:12.995Z",
      "status": 404,
      "error": "Not Found",
      "message": "Sorry, but this Size was not found",
      "path": "/v1/size",
      "code": "ITEM_007"
    }
    """

    const val SIZE_ALREADY_EXISTS = """
    {
    "timestamp": "2025-11-14T13:35:31.382915600Z",
    "status": 409,
    "error": "Conflict",
    "message": "Size value already exists",
    "path": "/v1/size",
    "code": "ITEM_007",
    "validationErrors": null
    }
    """
//endregion
}

