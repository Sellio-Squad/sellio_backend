package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.StoreRatingResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class StoreRatingDoc {
    @Operation(
        summary = "Get Store Rating",
        description = "Retrieve a specific Store rating info by storeId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Rating retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreRatingResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Rating info",
                                value = """
                        {
                            "id": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                            "averageRating": 4.0,
                            "totalRatings": 2,
                            "ratingCategorize": {
                                "5": 1,
                                "4": 0,
                                "3": 1,
                                "2": 0,
                                "1": 0
                            }
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
    annotation class GetRatingInfo

}