package org.shangahi.sellio_backend.api.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse

annotation class SubCategoryDoc {
    @Operation(
        summary = "Get SubCategory info by Category ID",
        description = "Retrieve SubCategories info by categoryId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "SubCategories retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SubCategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SubCategoriesinfo",
                                value = """
                           [
                                {
                                    "id": "33333333-d4d4-e5e5-f6f6-333333333333",
                                    "title": "Laptops",
                                    "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444",
                                    "categoryTitle": "Electronics",
                                    "createdAt": "2025-11-06T16:19:11.420487Z",
                                    "updatedAt": "2025-11-06T16:19:11.420487Z"
                                }
                            ]
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Category not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CategoryNotFoundExample",
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
    annotation class GetSubCategoryByCategoryId


    @Operation(
        summary = "Get SubCategory info by Store ID",
        description = "Retrieve SubCategories info by StoreId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "SubCategories retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SubCategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SubCategoriesinfo",
                                value = """
                           [
                                {
                                    "id": "33333333-d4d4-e5e5-f6f6-333333333333",
                                    "title": "Laptops",
                                    "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444",
                                    "categoryTitle": "Electronics",
                                    "createdAt": "2025-11-06T16:19:11.420487Z",
                                    "updatedAt": "2025-11-06T16:19:11.420487Z"
                                }
                            ]
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
                                name = "storeNotFoundExample",
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
    annotation class GetSubCategoryByStoreId
}