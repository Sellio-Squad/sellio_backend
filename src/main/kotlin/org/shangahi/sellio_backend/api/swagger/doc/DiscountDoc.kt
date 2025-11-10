package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.response.DiscountResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class DiscountDoc {
    @Operation(
        summary = "Get Discount info by storeId",
        description = "Use Store ID to Retrieve Discount info for specific store",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "discount retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DiscountResponse::class),
                        examples = [
                            ExampleObject(
                                name = "discount info",
                                value = """
                        {
                            "data": [
                                        {
                                            "id": "816f1cf6-bcf2-4440-bde4-225b4dcee1f1",
                                            "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                            "productId": null,
                                            "subCategoryId": null,
                                            "type": "PERCENTAGE",
                                            "value": 10.0,
                                            "startDate": "2025-11-06T16:19:11.437774Z",
                                            "endDate": "2025-12-06T16:19:11.437774Z"
                                        }
                                       ],
                            "totalElements": 1,
                            "page": 0,
                            "pageSize": 10,
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
    annotation class GetDiscountByStoreId

    @Operation(
        summary = "Get Discount info by productId",
        description = "Use product ID to Retrieve Discount info for specific product",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "discount retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DiscountResponse::class),
                        examples = [
                            ExampleObject(
                                name = "discount info",
                                value = """
                      
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "product not found",
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
    annotation class GetDiscountByProductId


    @Operation(
        summary = "Get Discount info by categoryId",
        description = "Use category ID to Retrieve Discount info for specific category",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "discount retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DiscountResponse::class),
                        examples = [
                            ExampleObject(
                                name = "discount info",
                                value = """
                       
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "category not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.CATEG_NOT_FOUND
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
    annotation class GetDiscountByCategoryId


    @Operation(
        summary = "Get Discount info by subCategoryId",
        description = "Use subCategory ID to Retrieve Discount info for specific subCategory",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "discount retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = DiscountResponse::class),
                        examples = [
                            ExampleObject(
                                name = "discount info",
                                value = """
                       
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "subCategory not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.SUBCATEG_NOT_FOUND
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
    annotation class GetDiscountBySubCategoryId


}