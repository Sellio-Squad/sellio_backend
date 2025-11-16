package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.FavoriteProductsResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class FavoriteStoresDoc {
    @Operation(
        summary = "Get user's favorite stores",
        description = "Retrieve a page of all favorite products for a specific user by userId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "page of favorite stores returned successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = FavoriteProductsResponse::class),
                        examples = [
                            ExampleObject(
                                name = "favorite stores",
                                value =
                                    """
                                                                    
                                {
                                  "data": [
                                        {
                                            "id": "440d426d-d0a7-4111-a5ec-532e5e2437f6",
                                            "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                            "userId": "f895cdbe-73fc-4e44-b5db-02f396953f64",
                                            "createdAt": "2025-11-07T14:36:58.886590Z"
                                        }
                                    ],
                                    "totalElements": 1,
                                    "page": 0,
                                    "pageSize": 10,
                                    "totalPages": 1
                                }                             
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
    annotation class GetFavoriteStores


    @Operation(
        summary = "Toggle favorite store for a user",
        description = "Add or remove a store from user's favorites based on current state",
        requestBody = RequestBody(
            required = true,
            description = "User ID and store ID to toggle favorite status",
            content = [
                Content(
                    mediaType = "application/json",
                    examples = [
                        ExampleObject(
                            name = "ToggleFavoriteExample",
                            value = """
                            {
                              "storeId": "57a212fc-e4ac-4f70-90cd-21f95dc600ba"
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
                                summary = "When store is added to favorites",
                                value = "\"store added to favorites successfully\""
                            ),
                            ExampleObject(
                                name = "RemovedFromFavorites",
                                summary = "When store is removed from favorites",
                                value = "\"store removed from favorites successfully\""
                            )
                        ]

                    )
                ],


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
                                name = "StoreNotFoundExample",
                                value = ErrorResponseExample.STORE_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
        ]
    )
    annotation class ToggleFavoriteStores
}
