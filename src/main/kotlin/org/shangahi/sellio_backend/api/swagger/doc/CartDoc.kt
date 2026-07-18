package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.AddCartItemRequest
import org.shangahi.sellio_backend.api.dto.request.UpdateCartItemRequest
import org.shangahi.sellio_backend.api.dto.response.CartResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class CartDoc {
    @Operation(
        summary = "Get current user's cart",
        description = "Retrieve the authenticated user's cart. Creates an empty cart if one does not exist.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Cart retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CartResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CartResponseExample",
                                value = """
                                {
                                    "id": "d1a2b3c4-e5f6-7890-abcd-ef1234567890",
                                    "items": [
                                        {
                                            "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                                            "productId": "00000000-0000-0000-0000-000000000001",
                                            "productTitle": "Wireless Headphones",
                                            "productImage": "https://example.com/images/headphones.jpg",
                                            "unitPrice": 79.99,
                                            "quantity": 2,
                                            "totalPrice": 159.98
                                        }
                                    ],
                                    "totalPrice": 159.98,
                                    "itemCount": 2
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "UnauthorizedExample",
                                value = ErrorResponseExample.AUTH_INVALID_CREDENTIALS
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
    annotation class GetCart

    @Operation(
        summary = "Add item to cart",
        description = "Add a product to the cart. If the product already exists, the quantity is increased.",
        requestBody = RequestBody(
            required = true,
            description = "Product ID and quantity to add",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = AddCartItemRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddCartItemExample",
                            value = """
                            {
                                "productId": "00000000-0000-0000-0000-000000000001",
                                "quantity": 2
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
                description = "Item added to cart successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CartResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request — invalid quantity or exceeds stock",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "QuantityExceedsStockExample",
                                value = ErrorResponseExample.CART_QUANTITY_EXCEEDS_STOCK
                            ),
                            ExampleObject(
                                name = "InvalidQuantityExample",
                                value = ErrorResponseExample.CART_INVALID_QUANTITY
                            ),
                            ExampleObject(
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.VALIDATION_ERROR
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
                            ExampleObject(
                                name = "ProductNotFoundExample",
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
    annotation class AddItem

    @Operation(
        summary = "Update cart item quantity",
        description = "Update the quantity of a specific item in the cart.",
        requestBody = RequestBody(
            required = true,
            description = "New quantity for the cart item",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UpdateCartItemRequest::class),
                    examples = [
                        ExampleObject(
                            name = "UpdateCartItemExample",
                            value = """
                            {
                                "quantity": 3
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
                description = "Item quantity updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CartResponse::class)
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
                                name = "QuantityExceedsStockExample",
                                value = ErrorResponseExample.CART_QUANTITY_EXCEEDS_STOCK
                            ),
                            ExampleObject(
                                name = "InvalidQuantityExample",
                                value = ErrorResponseExample.CART_INVALID_QUANTITY
                            ),
                            ExampleObject(
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.VALIDATION_ERROR
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Cart item not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CartItemNotFoundExample",
                                value = ErrorResponseExample.CART_ITEM_NOT_FOUND
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
    annotation class UpdateItemQuantity

    @Operation(
        summary = "Remove item from cart",
        description = "Remove a specific item from the cart by its ID.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Item removed from cart successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = CartResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Cart item not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CartItemNotFoundExample",
                                value = ErrorResponseExample.CART_ITEM_NOT_FOUND
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
    annotation class RemoveItem
}
