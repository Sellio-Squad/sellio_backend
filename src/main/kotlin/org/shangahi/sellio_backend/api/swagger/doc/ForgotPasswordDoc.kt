package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.*
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.OtpResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class ForgotPasswordDoc {

    @Operation(
        summary = "Request OTP to reset password",
        description = "Start the password reset flow by requesting OTP using phone number + region",
        requestBody = RequestBody(
            required = true,
            description = "Phone number + region",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = RequestOtpRequest::class),
                    examples = [
                        ExampleObject(
                            name = "RequestResetExample",
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
                        schema = Schema(implementation = OtpResponse::class),
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
                                name = "InvalidPhoneNumber",
                                value = ErrorResponseExample.UNVALID_PHONE_NUMBER
                            ),
                            ExampleObject(
                                name = "InvalidPhoneRegion",
                                value = ErrorResponseExample.INVALID_PHONE_NUMBER_REGION
                            )
                        ]
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
                                name = "UserNotFound",
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
                                name = "InternalServerError",
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
        summary = "Verify OTP for password reset",
        description = "Verify the OTP provided during forgot password to proceed with resetting password",
        requestBody = RequestBody(
            required = true,
            description = "Session ID + OTP",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = VerifyOtpRequest::class),
                    examples = [
                        ExampleObject(
                            name = "VerifyOtpExample",
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
                description = "Invalid or expired OTP",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "InvalidOtp", value = ErrorResponseExample.OTP_INVALID),
                            ExampleObject(name = "OtpExpired", value = ErrorResponseExample.OTP_EXPIRED)
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Session not found",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "SessionNotFound", value = ErrorResponseExample.USER_NOT_FOUND)
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
                            ExampleObject(name = "InternalServerError", value = ErrorResponseExample.INTERNAL_SERVER_ERROR)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class VerifyOtp

    @Operation(
        summary = "Reset password after OTP verification",
        description = "Submit new password using valid session ID from OTP verification",
        requestBody = RequestBody(
            required = true,
            description = "Session ID + new password",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = ResetPasswordRequest::class),
                    examples = [
                        ExampleObject(
                            name = "ResetPasswordExample",
                            value = """
                            {
                              "sessionId": "f47ac10b-58cc-4372-a567-0e02b2c3d479",
                              "newPassword": "NewPassword123!"
                            }
                            """
                        )
                    ]
                )
            ]
        ),
        responses = [
            ApiResponse(responseCode = "200", description = "Password reset successfully"),
            ApiResponse(
                responseCode = "400",
                description = "Password mismatch or validation error",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(name = "PasswordMismatch", value = ErrorResponseExample.REQUEST_BODY_ERROR)
                        ]
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Session not found",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(name = "SessionNotFound", value = ErrorResponseExample.USER_NOT_FOUND)
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
                            ExampleObject(name = "InternalServerError", value = ErrorResponseExample.INTERNAL_SERVER_ERROR)
                        ]
                    )
                ]
            )
        ]
    )
    annotation class ResetPassword
}
