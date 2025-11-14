package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.SizeRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.SizeResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class SizeDoc {
    @Operation(
        summary = "Get Size info by size",
        description = "Use Size ID to Retrieve specific size",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "size retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SizeResponse::class),
                        examples = [
                            ExampleObject(
                                name = "size info",
                                value = """
                        
                                 {
                                        "id": 4,
                                        "value": "Large"
                                    }
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "size not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "SizeNotFoundExample",
                                value = ErrorResponseExample.SIZE_NOT_FOUND
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
    annotation class GetSize

    @Operation(
        summary = "Get all available sizes",
        description = " Retrieve list of all available sizes",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "sizes retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SizeResponse::class),
                        examples = [
                            ExampleObject(
                                name = "size info",
                                value = """
                                    [
                                        {
                                            "id": 1,
                                            "value": "Small"
                                        },
                                        {
                                            "id": 2,
                                            "value": "Medium"
                                         },
                                        {
                                            "id": 4,
                                            "value": "Large"
                                        },
                                        {
                                            "id": 5,
                                            "value": "XL"
                                        },
                                        {
                                            "id": 6,
                                            "value": "2XL"
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
    annotation class GetAllSizes

    @Operation(
        summary = "Insert new size",
        description = "Insert new size value and make suer to don't add existed size",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to add new size",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SizeRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddSizeRequestExample",
                            value = """
                            {
                                "value": "3XL"
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
                description = "Size Inserted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SizeResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Size info",
                                value = """
                           {
                                "id": "3",
                                "value": "3XL"
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
                                name = "SizeAlreadyExistErrorExample",
                                value = ErrorResponseExample.SIZE_ALREADY_EXISTS
                            ),
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
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.VALIDATION_ERROR
                            ),
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
    annotation class InsertSize


    @Operation(
        summary = "Update size",
        description = "Update size value using id and make suer to don't add existed size",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to update new size",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = SizeRequest::class),
                    examples = [
                        ExampleObject(
                            name = "updateSizeRequestExample",
                            value = """
                            {
                                "value": "4XL"
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
                description = "Size updated successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = SizeResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Size info",
                                value = """
                           {
                                "id": "3",
                                "value": "4XL"
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
                                name = "SizeAlreadyExistErrorExample",
                                value = ErrorResponseExample.SIZE_ALREADY_EXISTS
                            ),
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
                responseCode = "404",
                description = "Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.SIZE_NOT_FOUND
                            ),

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
    annotation class UpdateSize


    @Operation(
        summary = "Delete size",
        description = "Delete size value using sizeId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Size deleted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "delete size message",
                                value = "\"size deleted successfully\""
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
                                name = "ValidationErrorExample",
                                value = ErrorResponseExample.SIZE_NOT_FOUND
                            ),
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
    annotation class DeleteSize


}