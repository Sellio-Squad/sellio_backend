package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.ConfirmOrderRequest
import org.shangahi.sellio_backend.api.dto.response.ConfirmOrderResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class OrderDoc {
    @Operation(
        summary = "Confirm order from cart",
        description = "Create orders from the current cart contents. Items are grouped by store into separate orders. The cart is cleared after successful placement.",
        requestBody = RequestBody(
            required = false,
            description = "Optional note for the order",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ConfirmOrderRequest::class),
                    examples = [
                        ExampleObject(
                            name = "ConfirmOrderExample",
                            value = """
                            {
                                "note": "Please deliver between 2-5 PM"
                            }
                            """
                        ),
                        ExampleObject(
                            name = "ConfirmOrderNoNoteExample",
                            value = "{}"
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Orders placed successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ConfirmOrderResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ConfirmOrderResponseExample",
                                value = """
                                {
                                    "message": "Orders placed successfully",
                                    "orderIds": [
                                        "d1a2b3c4-e5f6-7890-abcd-ef1234567890",
                                        "e5f6a1b2-c3d4-7890-abcd-ef1234567891"
                                    ]
                                }
                                """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request — cart is empty or quantity exceeds stock",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CartIsEmptyExample",
                                value = ErrorResponseExample.CART_IS_EMPTY
                            ),
                            ExampleObject(
                                name = "QuantityExceedsStockExample",
                                value = ErrorResponseExample.CART_QUANTITY_EXCEEDS_STOCK
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Cart or product not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CartNotFoundExample",
                                value = ErrorResponseExample.CART_NOT_FOUND
                            ),
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
    annotation class ConfirmOrder

    @Operation(
        summary = "Get order history",
        description = "Retrieve paginated order history for the authenticated user, optionally filtered by status.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Order history retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PageResponse::class),
                        examples = [
                            ExampleObject(
                                name = "OrderHistoryExample",
                                value = """
                                {
                                    "data": [
                                        {
                                            "orderId": "d1a2b3c4-e5f6-7890-abcd-ef1234567890",
                                            "orderDate": "2025-11-07T13:42:51.484Z",
                                            "status": "PROCESSING",
                                            "totalPrice": 159.98,
                                            "storeName": "Tech Store",
                                            "storeLogoUrl": "https://example.com/logos/tech.png",
                                            "items": [
                                                {
                                                    "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                                                    "productId": "00000000-0000-0000-0000-000000000001",
                                                    "productName": "Wireless Headphones",
                                                    "productImageUrl": "https://example.com/images/headphones.jpg",
                                                    "quantity": 2,
                                                    "price": 79.99,
                                                    "createdAt": "2025-11-07T13:42:51.484Z",
                                                    "updatedAt": "2025-11-07T13:42:51.484Z"
                                                }
                                            ]
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
    annotation class GetOrderHistory

    @Operation(
        summary = "Get completed order items",
        description = "Retrieve paginated list of all completed order items (admin).",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Completed items retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PageResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CompletedItemsExample",
                                value = """
                                {
                                    "data": [
                                        {
                                            "id": "a1b2c3d4-e5f6-7890-abcd-ef1234567890",
                                            "productId": "00000000-0000-0000-0000-000000000001",
                                            "productName": "Wireless Headphones",
                                            "productImageUrl": "https://example.com/images/headphones.jpg",
                                            "quantity": 1,
                                            "price": 79.99,
                                            "createdAt": "2025-11-07T13:42:51.484Z",
                                            "updatedAt": "2025-11-07T13:42:51.484Z"
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
    annotation class GetCompletedOrders

    @Operation(
        summary = "Cancel an order",
        description = "Cancel a PROCESSING order. Stock is restored and the order status is set to CANCELLED.",
        responses = [
            ApiResponse(
                responseCode = "204",
                description = "Order cancelled successfully"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request — order cannot be cancelled in its current status",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "OrderCannotBeCancelledExample",
                                value = ErrorResponseExample.ORDER_CANNOT_CANCEL
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Order not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "OrderNotFoundExample",
                                value = ErrorResponseExample.ORDER_NOT_FOUND
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
    annotation class CancelOrder
}
