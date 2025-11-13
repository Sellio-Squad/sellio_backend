package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.*
import org.shangahi.sellio_backend.api.dto.response.AuthResponse
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.OtpRequestResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class AccountDoc {

    @Operation(
        summary = "Login with phone number and password",
        description = "Authenticate user and return access and refresh tokens",
        requestBody = RequestBody(
            required = true,
            description = "Phone number and password",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = LoginRequest::class),
                    examples = [
                        ExampleObject(
                            name = "LoginRequestExample",
                            value = """
                            {
                              "phoneNumber": "07712345678",
                              "password": "12345678"
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
                description = "Login successful",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AuthResponse::class),
                        examples = [
                            ExampleObject(
                                name = "AuthResponseExample",
                                value = """
                                {
                                  "accessToken": "jwt_access_token_here",
                                  "refreshToken": "refresh_token_here"
                                }
                            """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Invalid credentials",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidCredentialsExample",
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
    annotation class Login

    @Operation(
        summary = "Request OTP for user registration",
        description = "Send an OTP to the phone number before creating a user",
        requestBody = RequestBody(
            required = true,
            description = "Phone number and default region",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RequestOtpRequest::class),
                    examples = [
                        ExampleObject(
                            name = "RequestOtpExample",
                            value = """
                            {
                              "phoneNumber": "07712345678",
                              "defaultRegion": "IQ"
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
                description = "OTP requested successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = OtpRequestResponse::class),
                        examples = [
                            ExampleObject(
                                name = "OtpRequestResponseExample",
                                value = """
                                {
                                  "sessionId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
                                }
                            """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid phone number or region",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidPhoneExample",
                                value = ErrorResponseExample.UNVALID_PHONE_NUMBER
                            ),
                            ExampleObject(
                                name = "InvalidPhoneRegionExample",
                                value = ErrorResponseExample.INVALID_PHONE_NUMBER_REGION
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Phone number already exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "PhoneNumberAlreadyExistsExample",
                                value = ErrorResponseExample.USER_PHONE_NUMBER_ALREADY_EXISTS
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
    annotation class RequestOtp

    @Operation(
        summary = "Verify OTP for registration",
        description = "Verify the OTP sent to the phone number to allow user creation",
        requestBody = RequestBody(
            required = true,
            description = "OTP and session ID received from request-otp",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VerifyOtpRequest::class),
                    examples = [
                        ExampleObject(
                            name = "VerifyOtpRequestExample",
                            value = """
                            {
                              "otp": "1234",
                              "sessionId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
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
                description = "OTP verified successfully"
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid OTP",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidOtpExample",
                                value = ErrorResponseExample.OTP_INVALID
                            ),
                            ExampleObject(
                                name = "OtpExpiredExample",
                                value = ErrorResponseExample.OTP_EXPIRED
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
    annotation class VerifyOtp

    @Operation(
        summary = "Create user after OTP verification",
        description = "Create a new user account. Requires OTP verification first",
        requestBody = RequestBody(
            required = true,
            description = "User registration data along with verified session ID",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CreateUserRequest::class),
                    examples = [
                        ExampleObject(
                            name = "CreateUserRequestExample",
                            value = """
                            {
                              "firstName": "Aziz",
                              "lastName": "Anwer",
                              "city": "Samarra",
                              "country": "Iraq",
                              "phoneNumber": "07712345678",
                              "password": "12345678",
                              "sessionId": "f47ac10b-58cc-4372-a567-0e02b2c3d479"
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
                description = "User created successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AuthResponse::class),
                        examples = [
                            ExampleObject(
                                name = "AuthResponseExample",
                                value = """
                                {
                                  "accessToken": "jwt_access_token_here",
                                  "refreshToken": "refresh_token_here"
                                }
                            """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Invalid OTP or missing session",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidOtpExample",
                                value = ErrorResponseExample.OTP_INVALID
                            ),
                            ExampleObject(
                                name = "OtpExpiredExample",
                                value = ErrorResponseExample.OTP_EXPIRED
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "409",
                description = "Phone number already exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "PhoneNumberAlreadyExistsExample",
                                value = ErrorResponseExample.USER_PHONE_NUMBER_ALREADY_EXISTS
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
    annotation class CreateUser

    @Operation(
        summary = "Refresh access token using refresh token",
        description = "Validate the refresh token and issue new access and refresh tokens",
        requestBody = RequestBody(
            required = true,
            description = "Refresh token",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RefreshTokenRequest::class),
                    examples = [
                        ExampleObject(
                            name = "RefreshTokenRequestExample",
                            value = """
                            {
                              "refreshToken": "refresh_token_here"
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
                description = "Token refreshed successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = AuthResponse::class),
                        examples = [
                            ExampleObject(
                                name = "AuthResponseExample",
                                value = """
                                {
                                  "accessToken": "jwt_access_token_here",
                                  "refreshToken": "refresh_token_here"
                                }
                            """
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "401",
                description = "Invalid refresh token",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidRefreshTokenExample",
                                value = ErrorResponseExample.AUTH_INVALID_REFRESH_TOKEN
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
    annotation class RefreshToken
}
