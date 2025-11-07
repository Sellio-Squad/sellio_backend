package org.shangahi.sellio_backend.api.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.response.CategoryResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse

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
}