package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.ProductItemRequest
import org.shangahi.sellio_backend.api.dto.request.ProductItemUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.PageResponse
import org.shangahi.sellio_backend.api.dto.response.ProductItemResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample


annotation class ProductItemDoc {

    @Operation(
        summary = "Get all items for a product",
        description = "Retrieve a list of all product items (variations like colors, sizes) linked to a specific Product ID.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Product items retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductItemResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ProductItem list",
                                value = """
                                    
                                        [
                                              {
                                                  "id": "a1a1a1a1-aaaa-aaaa-aaaa-000000000001",
                                                  "price": 1500.0,
                                                  "discountId": null,
                                                  "variationImageUrl": null,
                                                  "colorId": null,
                                                  "sizeId": null,
                                                  "stock": 5
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
    annotation class GetProductItems

    @Operation(
        summary = "Insert a single new product item (variation)",
        description = "Add a new item (e.g., 'Red, Large, 10 stock') to an existing product.",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields for the new product item.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductItemRequest::class),
                    examples = [
                        ExampleObject(
                            name = "InsertItemRequestExample",
                            value = """
                            {
                                "price": 260.0,
                                "stock": 50,
                                "colorId": 2,
                                "sizeId": 3,
                                "variationImageUrl": "https://s3.aws/tshirt-green-medium.jpg"
                            }
                        """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Product item inserted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductItemResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ProductNotFound",
                                value = ErrorResponseExample.PROD_NOT_FOUND
                            ),
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad request (Validation error)",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.VALIDATION_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class InsertProductItem

    @Operation(
        summary = "Insert a list of new product items (batch)",
        description = "Add a batch (list) of new items (e.g., all colors and sizes) to an existing product in one call.",
        requestBody = RequestBody(
            required = true,
            description = "Insert a list of product items.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductItemRequest::class),
                    examples = [
                        ExampleObject(
                            name = "InsertItemsListRequestExample",
                            value = """
                            [
                                {
                                    "price": 260.0,
                                    "stock": 50,
                                    "colorId": 3,
                                    "sizeId": 4
                                },
                                {
                                    "price": 260.0,
                                    "stock": 50,
                                    "colorId": 2,
                                    "sizeId": 2
                                }
                            ]
                        """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(
                responseCode = "201",
                description = "Product items list inserted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductItemResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found (Product, Color, Size, etc.)",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ProductNotFound",
                                value = ErrorResponseExample.PROD_NOT_FOUND
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class AddProductItems

    @Operation(
        summary = "Update an existing product item",
        description = "Update details (like price, stock) of a specific product item using its ID.",
        requestBody = RequestBody(
            required = true,
            description = "Fields to update. Only send the fields you want to change.",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ProductItemUpdateRequest::class),
                    examples = [
                        ExampleObject(
                            name = "UpdateItemRequestExample",
                            value = """
                            {
                                "price": 275.0,
                                "stock": 40
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
                description = "Product item updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ProductItemResponse::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ProductItemNotFound",
                                value = ErrorResponseExample.ITEM_NOT_FOUND
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class UpdateProductItem

    @Operation(
        summary = "Delete a specific product item",
        description = "Delete a specific product item (variation) by its ID. Fails if the item is in a cart or order.",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Product item deleted successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "Delete message",
                                value = "\"Product item deleted successfully\""
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ProductItemNotFound",
                                value = ErrorResponseExample.ITEM_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request (Item in use)",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ItemInUseError",
                                value = ErrorResponseExample.ITEM_IN_USE
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class DeleteProductItem
}