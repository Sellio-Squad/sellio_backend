package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.CreateStoreRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.StoreCreationResponse
import org.shangahi.sellio_backend.api.dto.response.StoreInfoResponse
import org.shangahi.sellio_backend.api.dto.response.StoreResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class StoreDoc {
    @Operation(
        summary = "Get Store info by store ID",
        description = "Retrieve a specific store info by storeId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Store retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreInfoResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Store info",
                                value = """
                           {
    "id": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
    "ownerId": "f895cdbe-73fc-4e44-b5db-02f396953f64",
    "title": "Global Tech Store",
    "description": "High-end electronics store for developers.",
    "avatarImageURL": null,
    "coverImageURL": null,
    "phoneNumber": "01212121212",
    "city": "Maadi",
    "government": "Cairo",
    "country": "Egypt",
    "featuredProducts": [
        {
            "id": "11111111-a1a1-b2b2-c3c3-111111111111",
            "title": "Razer Gaming Laptop",
            "price": 1500.0,
            "mainImageUrl": null
        }
    ],
    "avgRating": 4.0,
    "activeStoreDiscounts": [
        {
            "id": "d53bae1d-ef71-4f72-8916-952a2663c6d4",
            "type": "PERCENTAGE",
            "value": 10.0,
            "endDate": "2025-12-13T02:00:56.975107Z"
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
    annotation class GetStoreInfo

    @Operation(
        summary = "Create a new store",
        description = "Creates a new store for the authenticated user. The request must be sent as multipart/form-data and include the store images.",
        requestBody = RequestBody(
            required = true,
            description = "Store information and images",
            content = [
                Content(
                    mediaType = "multipart/form-data",
                    schema = Schema(implementation = CreateStoreRequest::class),
                    examples = [
                        ExampleObject(
                            name = "CreateStoreRequestExample",
                            value = """
                        {
                          "title": "Laptop Store",
                          "description": "Store for selling laptops",
                          "city": "Cairo",
                          "country": "Egypt",
                          "categoryIds": [
                            "be136218-f697-4289-85f7-1aaf904f9035"
                          ],
                          "avatarImage": "<image file>",
                          "coverImage": "<image file>"
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
                description = "Store created successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreCreationResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreCreatedResponse",
                                value = """
                            {
                              "id": "be136218-f697-4289-85f7-1aaf904f9035",
                              "title": "Laptop Store",
                              "avatarUrl": "https://cdn.example.com/stores/avatars/avatar.jpg",
                              "coverUrl": "https://cdn.example.com/stores/covers/cover.jpg",
                              "createdAt": "2025-11-10T17:13:24.908618400Z"
                            }
                            """
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
                                name = "RequestBodyError",
                                value = ErrorResponseExample.REQUEST_BODY_ERROR
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Conflict",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "StoreTitleAlreadyExists",
                                value = ErrorResponseExample.STORE_TITLE_ALREADY_EXISTS
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Referenced category not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "CategoryNotFound",
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
                                name = "InternalServerError",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class CreateStore

    @Operation(
        summary = "Top Stores",
        description = "Get page of top rated stores",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "top stores retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Store info",
                                value = """
                        {
                            "data": [
                                {
                                    "id": "57a212fc-e4ac-4f70-90cd-21f95dc600ba",
                                    "title": "Global Tech Store",
                                    "city": "Maadi",
                                    "government": "Cairo",
                                    "country": "Egypt",
                                    "avatarImageURL": null,
                                    "coverImageURL": null
                                }
                            ],
                            "totalElements": 1,
                            "page": 0,
                            "pageSize": 20,
                            "totalPages": 1
                        }
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
            ),
        ]
    )
    annotation class TopStores


    @Operation(
        summary = "Search by store title and city",
        description = "Get page of stores related to typed query and provided city",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "search result retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreResponse::class),
                        examples = [
                            ExampleObject(
                                name = "search result",
                                value = """
                                    {
  "data": [
    {
      "id": "be136218-f697-4289-85f7-1aaf904f9035",
      "title": "laptop-store",
      "city": "cairo",
      "government": "cairo",
      "country": "egypt",
      "avatarImageURL": null,
      "coverImageURL": null
    }
  ],
  "totalElements": 1,
  "page": 0,
  "pageSize": 20,
  "totalPages": 1
}
                        
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
            ),
        ]
    )
    annotation class SearchByStoreTitle

    @Operation(
        summary = "Get Store by their Owner request",
        responses = [
            ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreResponse::class),
                        examples = [
                            ExampleObject(
                                name = "store result",
                                value = """
                                    {
  {
    "id": "c2488bcb-170d-487f-9f7a-9592d4c558ef",
    "title": "nice store",
    "city": "samarra",
    "government": "saladin",
    "country": "iraq",
    "avatarImageURL": null,
    "coverImageURL": null
}
                        
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
            ),
        ]
    )
    annotation class StoreOwner

}