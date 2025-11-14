package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.ColorRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.ColorResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class ColorDoc {
    @Operation(
        summary = "Get Color info by Color",
        description = "Use Color ID to Retrieve specific Color",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Color retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ColorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Color info",
                                value = """
                        
                                 {
                                        "id": 2,
                                        "value": "Black"
                                    }
                        """
                            )
                        ],
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Color not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "ColorNotFoundExample",
                                value = ErrorResponseExample.COLOR_NOT_FOUND
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
    annotation class GetColor

    @Operation(
        summary = "Get all available Colors",
        description = " Retrieve list of all available Colors",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Colors retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ColorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Color info",
                                value = """
                                    [
                                        {
        "id": 10,
        "value": "black"
    },
    {
        "id": 1,
        "value": "Red"
    },
    {
        "id": 2,
        "value": "Black"
    },
    {
        "id": 11,
        "value": "red"
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
    annotation class GetAllColors

    @Operation(
        summary = "Insert new Color",
        description = "Insert new Color value and make suer to don't add existed Color",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to add new Color",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ColorRequest::class),
                    examples = [
                        ExampleObject(
                            name = "AddColorRequestExample",
                            value = """
                            {
                                "value": "dark-red"
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
                description = "Color Inserted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ColorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Color info",
                                value = """
                           {
                                "id": "12",
                                "value": "dark-red"
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
                                name = "ColorAlreadyExistErrorExample",
                                value = ErrorResponseExample.COLOR_ALREADY_EXISTS
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
    annotation class InsertColor


    @Operation(
        summary = "Update Color",
        description = "Update Color value using id and make suer to don't add existed Color",
        requestBody = RequestBody(
            required = true,
            description = "Insert required fields to update new Color",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ColorRequest::class),
                    examples = [
                        ExampleObject(
                            name = "updateColorRequestExample",
                            value = """
                            {
                                "value": "dark-blue"
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
                description = "Color updated successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ColorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "Color info",
                                value = """
                           {
                                "id": "12",
                                "value": "dark-blue"
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
                                name = "ColorAlreadyExistErrorExample",
                                value = ErrorResponseExample.COLOR_ALREADY_EXISTS
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
                                value = ErrorResponseExample.COLOR_NOT_FOUND
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
    annotation class UpdateColor


    @Operation(
        summary = "Delete Color",
        description = "Delete Color value using ColorId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Color deleted successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "delete Color message",
                                value = "\"Color deleted successfully\""
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
                                value = ErrorResponseExample.COLOR_NOT_FOUND
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
    annotation class DeleteColor


}