package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.CategoryRequest
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class CategoryDoc {
    @Operation(
        summary = "Get Category info by ID",
        description = "Retrieve a specific Category info by categoryId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Category retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Category info",
                                value = """
                        {
                            "id": "44444444-c5c5-d6d6-e7e7-444444444444",
                            "title": "Electronics",
                            "createdAt": "2025-11-06T16:19:11.418844Z",
                            "updatedAt": "2025-11-06T16:19:11.418844Z",
                            "subCategories": [
                                {
                                    "id": "33333333-d4d4-e5e5-f6f6-333333333333",
                                    "title": "Laptops",
                                    "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444",
                                    "categoryTitle": "Electronics",
                                    "createdAt": "2025-11-06T16:19:11.420487Z",
                                    "updatedAt": "2025-11-06T16:19:11.420487Z"
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
    annotation class GetCategoryInfo


    @Operation(
        summary = "Get all categories",
        description = "Retrieve all categories",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Category retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "All categories",
                                value = """
                     [ 
                        {
                            "id": "44444444-c5c5-d6d6-e7e7-444444444444",
                            "title": "Electronics",
                            "createdAt": "2025-11-06T16:19:11.418844Z",
                            "updatedAt": "2025-11-06T16:19:11.418844Z",
                            "subCategories": [
                                {
                                    "id": "33333333-d4d4-e5e5-f6f6-333333333333",
                                    "title": "Laptops",
                                    "categoryId": "44444444-c5c5-d6d6-e7e7-444444444444",
                                    "categoryTitle": "Electronics",
                                    "createdAt": "2025-11-06T16:19:11.420487Z",
                                    "updatedAt": "2025-11-06T16:19:11.420487Z"
                                }
                            ]
                        }
                          ]
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
            )
        ]
    )
    annotation class GetAllCategories


    @Operation(
        summary = "Insert new Category",
        description = "Insert Category by providing title",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to add new user",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CategoryRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddCategoryRequestExample",
                            value = """
                           {
                              "title": "PC-Accessory"
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
                description = "Category Inserted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Category info",
                                value = """
                           { 
                               "id": "a67130bb-a1ee-417e-9394-48ed96a25a31",
                               "title": "PC-Accessory",
                               "createdAt": "2025-11-09T23:18:09.950667Z",
                               "updatedAt": "2025-11-09T23:18:09.950667Z",
                               "subCategories": []
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
                                name = "CategoryTitleAlreadyExistsExample",
                                value = ErrorResponseExample.CATEG_ALREADY_EXISTS
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
    annotation class InsertCategory

    @Operation(
        summary = "Get Custom Product Categories",
        description = "Retrieves a list of all main categories that contain customizable products. These are linked to the official 'Sellio Customize' store.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Categories retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CategoryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Custom Categories List",
                                value = """
                                   [
                                    {
                                        "id": "a0a0a0a0-0001-0001-0001-000000000001",
                                        "title": "Apparel",
                                        "createdAt": "2025-11-13T02:00:56.997798Z",
                                        "updatedAt": "2025-11-13T02:00:56.997798Z",
                                        "subCategories": [
                                            {
                                                "id": "b0b0b0b0-0001-0001-0001-000000000001",
                                                "title": "T-Shirts",
                                                "categoryId": "a0a0a0a0-0001-0001-0001-000000000001",
                                                "categoryTitle": "Apparel",
                                                "createdAt": "2025-11-13T02:00:56.998885Z",
                                                "updatedAt": "2025-11-13T02:00:56.998885Z"
                                            }
                                                        ]
                                    }
                                ]
                                """
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
    annotation class GetCustom
}