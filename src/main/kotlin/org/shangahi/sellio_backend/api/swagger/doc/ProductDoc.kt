package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.ProductRequest
import org.shangahi.sellio_backend.api.dto.response.ProductResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class ProductDoc {
    @Operation(
        summary = "Get Product info by product ID",
        description = "Retrieve a specific product info by productId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "product retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Store info",
                                value = """
                            {
                                "id": "11111111-a1a1-b2b2-c3c3-111111111111",
                                "title": "Razer Gaming Laptop",
                                "description": "High-end gaming machine for test.",
                                "mainImageURL": null,
                                "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                "price": 1500.0,
                                "isUsed": false,
                                "isFeatured": true,
                                "subCategoryIds": [
                                    "33333333-d4d4-e5e5-f6f6-333333333333"
                                ],
                                "imageUrls": [
                                    "https://s3.aws/image_a1.jpg"
                                ],
                                "items": [
                                    {
                                        "id": "a1a1a1a1-aaaa-aaaa-aaaa-000000000001",
                                        "price": 1500.0,
                                        "discountId": null,
                                        "colorId": null,
                                        "sizeId": null,
                                        "weightId": 2,
                                        "stock": 5
                                    }
                                ]
                            }
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.PROD_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalServerErrorExample",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class GetProductById


    @Operation(
        summary = "Insert new product",
        description = "Insert new product info ",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to add new store",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddProductRequestExample",
                            value = """
                                {
  "title": "keyboard",
  "description": "this is new keyboard product",
  "mainImageURL": "imge/url",
  "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
  "price": 500.0,
  "isFeatured": true,
  "subCategoryIds": [
    "33333333-d4d4-e5e5-f6f6-333333333333"
  ],
  "imageUrls": [
    "image/url1",
    "image2/url"
  ],
  "items": [
    {
      "price": 500.0,
      "discountId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "colorId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "sizeId": "3fa85f64-5717-4562-b3fc-2c963f66afa6",
      "weightId": 0,
      "stock": 0
    }
  ]
}
                            
                        """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "product Inserted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Product info",
                                value = """
                           {
    "id": "b0f14088-bd1e-4524-93e9-f620e0ebb531",
    "title": "keyboard",
    "description": "this is new keyboard product",
    "mainImageURL": "imge/url",
    "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
    "price": 500.0,
    "isUsed": false,
    "isFeatured": true,
    "subCategoryIds": [],
    "imageUrls": [],
    "items": []
}
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "MissedFieldErrorExample",
                                value = ErrorResponseExample.REQUEST_BODY_ERROR
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.STORE_NOT_FOUND
                            ),
                            ExampleObject(
                                name = "SubCategoryNotFoundExample",
                                value = ErrorResponseExample.SUBCATEG_NOT_FOUND
                            ),
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalServerErrorExample",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            ),
        ]
    )
    annotation class CreateProduct


    @Operation(
        summary = "Thrift products",
        description = "Get a page of used product",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Thrifts retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "thrift products info",
                                value = """
                        {
    "data": [
        {
            "id": "19e9dfa4-6b40-4729-9d41-f283f361b8af",
            "title": "gaming keyboard",
            "description": "this is new keyboard product",
            "mainImageURL": "imge/url",
            "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
            "price": 500.0,
            "isUsed": true,
            "isFeatured": true,
            "subCategoryIds": [
                "33333333-d4d4-e5e5-f6f6-333333333333"
            ],
            "imageUrls": [
                "image/url1",
                "image2/url"
            ],
            "items": [
                {
                    "id": "14b6c302-2828-4808-a4d8-387bebe46d06",
                    "price": 500.0,
                    "discountId": null,
                    "colorId": null,
                    "sizeId": null,
                    "weightId": null,
                    "stock": 0
                }
            ]
        }
    ],
    "totalElements": 1,
    "page": 0,
    "pageSize": 20,
    "totalPages": 1
}
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalServerErrorExample",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            ),
        ]
    )
    annotation class Thrift


    @Operation(
        summary = "Search by product title",
        description = "Get page of products related to typed query",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "search result retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "search result",
                                value = """
                                    {
    "data": [
        {
            "id": "11111111-a1a1-b2b2-c3c3-111111111111",
            "title": "Razer Gaming Laptop",
            "price": 1500.0,
            "mainImageUrl": null
        },
        {
            "id": "19e9dfa4-6b40-4729-9d41-f283f361b8af",
            "title": "gaming keyboard",
            "price": 500.0,
            "mainImageUrl": "imge/url"
        }
    ],
    "totalElements": 2,
    "page": 0,
    "pageSize": 20,
    "totalPages": 1
}
                        
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalServerErrorExample",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            ),
        ]
    )
    annotation class SearchByProductTitle

    @Operation(
        summary = "Get Products info by Store ID",
        description = "Retrieve specific store's products info by storeId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "products retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductResponse::class),
                        examples = [
                            ExampleObject(
                                name = "products info",
                                value = """
                            {
    "data": [
        {
            "id": "11111111-a1a1-b2b2-c3c3-111111111111",
            "title": "Razer Gaming Laptop",
            "price": 1500.0,
            "mainImageUrl": null
        },
        {
            "id": "b0f14088-bd1e-4524-93e9-f620e0ebb531",
            "title": "keyboard",
            "price": 500.0,
            "mainImageUrl": "imge/url"
        },
        {
            "id": "19e9dfa4-6b40-4729-9d41-f283f361b8af",
            "title": "gaming keyboard",
            "price": 500.0,
            "mainImageUrl": "imge/url"
        },
        {
            "id": "22222222-a1a1-b2b2-c3c3-222222222222",
            "title": "Budget Office PC",
            "price": 900.0,
            "mainImageUrl": null
        }
    ],
    "totalElements": 4,
    "page": 0,
    "pageSize": 20,
    "totalPages": 1
}
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Store not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.STORE_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalServerErrorExample",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class GetProductByStoreId


}