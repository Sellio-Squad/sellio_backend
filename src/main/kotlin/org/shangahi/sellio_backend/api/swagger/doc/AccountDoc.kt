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
        summary = "Prepare signup and request OTP",
        description = "Collect user information, validate phone number, store pending signup, and send OTP",
        requestBody = RequestBody(
            required = true,
            description = "User registration data including phone and region",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = CreateUserRequest::class),
                    examples = [
                        ExampleObject(
                            name = "PrepareSignupExample",
                            value = """
                            {
                              "firstName": "Aziz",
                              "lastName": "Anwer",
                              "phoneNumber": "07712345678",
                              "password": "12345678",
                              "city": "Samarra",
                              "country": "Iraq",
                              "email": "aziz@example.com",
                              "region": "IQ"
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
    annotation class CreateUser

    @Operation(
        summary = "Verify OTP and create user",
        description = "Verify the OTP sent to the phone number and create a user account. Deletes pending signup after successful creation.",
        requestBody = RequestBody(
            required = true,
            description = "OTP and session ID from prepare signup",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VerifyOtpRequest::class),
                    examples = [
                        ExampleObject(
                            name = "VerifyOtpAndCreateUserExample",
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
                description = "Invalid OTP or expired session",
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
    annotation class VerifyOtp

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


    @Operation(
        summary = "Change password for logged-in user",
        description = "User must be authenticated. Requires current password and new password.",
        requestBody = RequestBody(
            required = true,
            description = "Current + new passwords",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ChangePasswordRequest::class),
                    examples = [
                        ExampleObject(
                            name = "ChangePasswordExample",
                            value = """
                            {
                              "currentPassword": "OldPassword123!",
                              "newPassword": "NewPassword123!",
                              "confirmPassword": "NewPassword123!"
                            }
                            """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Password updated successfully"),
            ApiResponse(
                responseCode = "401",
                description = "Invalid current password",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InvalidCurrentPassword",
                                value = ErrorResponseExample.AUTH_INVALID_CREDENTIALS
                            )
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "New password mismatch or validation error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "PasswordMismatch", value = ErrorResponseExample.REQUEST_BODY_ERROR)
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "500",
                description = "Internal server error",
                content = [
                    Content(
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
    annotation class ChangePassword
}
