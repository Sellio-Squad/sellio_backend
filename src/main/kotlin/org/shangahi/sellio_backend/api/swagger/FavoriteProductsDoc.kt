package org.shangahi.sellio_backend.api.swagger

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.FavoriteToggleRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse

annotation class FavoriteProductDocs {
    @Operation(
        summary = "Get user's favorite products",
        description = "Retrieve a list of all favorite products for a specific user by userId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "List of favorite products returned successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FavoriteProductsResponse::class),
                        examples = [
                            ExampleObject(
                                name = "favorite products",
                                value =
                                    """
                                    
                                   [
                                        {
                                            "id": "214509f9-627e-42d2-b5ed-0fcc105707f8",
                                            "productId": "11111111-a1a1-b2b2-c3c3-111111111111",
                                            "userId": "f895cdbe-73fc-4e44-b5db-02f396953f64",
                                            "createdAt": "2025-11-06T12:27:56.698566Z"
                                        }
                                   
                                     ]
                                     
                                     """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "UserNotFoundExample",
                                value = ErrorResponseExample.USER_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Unexpected server error",
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
    annotation class GetFavorites


    @Operation(
        summary = "Toggle favorite product for a user",
        description = "Add or remove a product from user's favorites based on current state",
        requestBody = RequestBody(
            required = true,
            description = "User ID and Product ID to toggle favorite status",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = FavoriteToggleRequest::class),
                    examples = [
                        ExampleObject(
                            name = "ToggleFavoriteExample",
                            value = """
                            {
                              "userId": "f895cdbe-73fc-4e44-b5db-02f396953f64",
                              "productId": "11111111-a1a1-b2b2-c3c3-111111111111"
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
                description = "Favorite toggled successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "AddedToFavorites",
                                summary = "When product is added to favorites",
                                value = "\"Product added to favorites successfully\""
                            ),
                            ExampleObject(
                                name = "RemovedFromFavorites",
                                summary = "When product is removed from favorites",
                                value = "\"Product removed from favorites successfully\""
                            )
                        ]

                    )
                ],


                ),
            ApiResponse(
                responseCode = "404",
                description = "User or product not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "UserNotFoundExample",
                                value = ErrorResponseExample.USER_NOT_FOUND
                            ),
                            ExampleObject(
                                name = "ProductNotFoundExample",
                                value = ErrorResponseExample.PROD_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
        ]
    )
    annotation class ToggleFavorite
}
