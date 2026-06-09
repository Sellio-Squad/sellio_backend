package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.ProductRatingRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.MessageResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingResponse
import org.shangahi.sellio_backend.api.dto.response.ProductRatingSummaryResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class ProductRatingDoc {

    @Operation(
        summary = "Add or update a product rating",
        description = "Submit a rating for a product. Replaces the existing rating if the user has already rated it. Requires a COMPLETED or CANCELLED order for the product.",
        requestBody = RequestBody(
            required = true,
            description = "Rating details (1-5 stars and a comment)",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductRatingRequest::class),
                    examples = [
                        ExampleObject(
                            name = "ValidRating",
                            value = """
                            {
                                "ratingValue": 5,
                                "comment": "Excellent quality and fast shipping!"
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
                description = "Rating added or updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = MessageResponse::class),
                        examples = [
                            ExampleObject(
                                name = "MessageResponse",
                                value = """
                                {
                                    "message": "Rating added or updated successfully"
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Validation Error (e.g., rating out of range)",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "ValidationError", value = ErrorResponseExample.VALIDATION_ERROR)
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Product or User not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "ProductNotFound", value = ErrorResponseExample.PROD_NOT_FOUND)
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
                            ExampleObject(name = "InternalError", value = ErrorResponseExample.INTERNAL_SERVER_ERROR)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class AddRating

    @Operation(
        summary = "Get paginated product ratings",
        description = "Retrieve all ratings for a specific product.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Ratings retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PageResponse::class),
                        examples = [
                            ExampleObject(
                                name = "PageOfRatings",
                                value = """
                                {
                                    "data": [
                                        {
                                            "id": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                            "userName": "John Doe",
                                            "userAvatarUrl": null,
                                            "ratingValue": 4,
                                            "comment": "Good product.",
                                            "createdAt": "2025-11-06T16:19:11.418844Z",
                                            "deletable": false,
                                            "editable": false
                                        }
                                    ],
                                    "totalElements": 1,
                                    "page": 0,
                                    "pageSize": 20,
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
                description = "Product not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "ProductNotFound", value = ErrorResponseExample.PROD_NOT_FOUND)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class GetProductRatings

    @Operation(
        summary = "Get product rating summary",
        description = "Retrieve the average rating, total count, and a breakdown of 1-5 star reviews for a product. Includes a flag indicating if the current user can review the product.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Summary retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductRatingSummaryResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SummaryResponse",
                                value = """
                                {
                                    "productId": "11111111-a1a1-b2b2-c3c3-111111111111",
                                    "averageRating": 4.5,
                                    "totalRatings": 2,
                                    "ratingCategorize": {
                                        "5": 1,
                                        "4": 1,
                                        "3": 0,
                                        "2": 0,
                                        "1": 0
                                    },
                                    "canReview": true,
                                    "userReview": null,
                                    "recentReviews": [
                                        {
                                            "id": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                            "userName": "Jane Smith",
                                            "userAvatarUrl": null,
                                            "ratingValue": 4,
                                            "comment": "Good product.",
                                            "createdAt": "2025-11-06T16:19:11.418844Z",
                                            "deletable": false,
                                            "editable": false
                                        }
                                    ]
                                }
                                """
                            )
                        ]
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
                            ExampleObject(name = "ProductNotFound", value = ErrorResponseExample.PROD_NOT_FOUND)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class GetProductRatingSummary

    @Operation(
        summary = "Delete a product rating",
        description = "Deletes a specific product rating. The user must be the author of the rating.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Rating deleted successfully"
            ),
            ApiResponse(
                responseCode = "404",
                description = "Rating not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error (e.g., unauthorized to delete)",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "InternalError", value = ErrorResponseExample.INTERNAL_SERVER_ERROR)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class DeleteRating
}
