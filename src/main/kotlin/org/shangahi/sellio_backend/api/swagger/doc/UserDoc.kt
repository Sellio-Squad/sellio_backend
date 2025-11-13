package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class UserDoc {
    @Operation(
        summary = "Get User info by user ID",
        description = "Retrieve a specific user info by userId",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "User retrieved successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserInfoResponse::class),
                        examples = [
                            ExampleObject(
                                name = "User info",
                                value = """
                            {
                                "id": "f895cdbe-73fc-4e44-b5db-02f396953f64",
                                "firstName": "Ahmed",
                                "lastName": "Sayed",
                                "email": "ahmed@sellio.com",
                                "phoneNumber": "01000000001",
                                "city": "Cairo",
                                "country": "Egypt",
                                "avatarUrl": null
                            }
                        """
                            )
                        ],
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
    annotation class GetUserInfo


    @Operation(
        summary = "Update user",
        description = "Get user by ID and Update user info  ",
        requestBody = RequestBody(
            required = false,
            description = "you can update at least one field into user info",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = UserUpdateRequest::class),
                    examples = [
                        ExampleObject(
                            name = "UpdateUserRequestExample",
                            value = """
                            {
                              "firstName": "not Ahmed"
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
                description = "User Info Updated successfully",

                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = UserInfoResponse::class),
                        examples = [
                            ExampleObject(
                                name = "User info",
                                value = """
                           {
                              "id": "e8387af4-0646-403a-9bfb-b718252ce48b",
                              "firstName": "Not Ahmed",
                              "lastName": "Sayed",
                              "email": null,
                              "phoneNumber": "01111111111",
                              "city": "Cairo",
                              "country": "Egypt",
                              "avatarUrl": null

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
                                name = "PhoneNumberAlreadyExistErrorExample",
                                value = ErrorResponseExample.USER_PHONE_NUMBER_ALREADY_EXISTS
                            ),
                            ExampleObject(
                                name = "EmailAlreadyExistErrorExample",
                                value = ErrorResponseExample.USER_EMAIL_ALREADY_EXISTS
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
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "User Not Found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "UserNotFoundErrorExample",
                                value = ErrorResponseExample.USER_NOT_FOUND
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
    annotation class UpdateUser
}