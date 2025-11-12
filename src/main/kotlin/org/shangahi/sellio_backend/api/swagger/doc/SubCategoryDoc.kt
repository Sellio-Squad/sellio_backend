package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.SubCategoryRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.SubCategoryResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

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

    @Operation(
        summary = "Insert new subcategory",
        description = "Insert subcategory by providing title and parent category ID",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to add new user",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SubCategoryRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddSubCategoryRequestExample",
                            value = """
                            {
                               "title": "PC-Screens",
                               "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444"
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
                description = "subCategory Inserted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SubCategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SubCategory info",
                                value = """
                           {
                               "id": "16a44747-ed89-43cc-9a75-1dcae4617e25",
                               "title": "PC-Screens",
                               "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444",
                               "categoryTitle": "Electronics",
                               "createdAt": "2025-11-09T22:53:51.030604Z",
                               "updatedAt": "2025-11-09T22:53:51.030604Z"
                           }
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Conflict errors",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SubCategoryTitleAlreadyExistsExample",
                                value = ErrorResponseExample.SUBCATEG_ALREADY_EXISTS
                            )
                        ]
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
                                name = "NotFoundErrorExample",
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
            ),
        ]
    )
    annotation class InsertSubCategory
}