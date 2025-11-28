package org.shangahi.sellio_backend.api.swagger.doc

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.parameters.RequestBody
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.shangahi.sellio_backend.api.dto.request.StoreContactCreateRequest
import org.shangahi.sellio_backend.api.dto.response.ErrorResponse
import org.shangahi.sellio_backend.api.dto.response.StoreContactResponse
import org.shangahi.sellio_backend.api.swagger.ErrorResponseExample

annotation class StoreContactDoc {

    @Operation(
        summary = "Add contacts to a store",
        description = "Create one or more contact entries for a store",
        requestBody = RequestBody(
            required = true,
            description = "Store contacts list",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = StoreContactCreateRequest::class),
                    examples = [
                        ExampleObject(
                            name = "CreateContactsExample",
                            value = """
                            {
                              "contacts": [
                                { "type": "EMAIL", "value": "store@example.com" },
                                { "type": "PHONE", "value": "07712345678" }
                              ]
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
                description = "Contacts created successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreContactResponse::class)
                    )
                ]
            ),

            ApiResponse(
                responseCode = "404",
                description = "Store not found",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "StoreNotFound",
                                value = ErrorResponseExample.STORE_NOT_FOUND
                            )
                        ]
                    )
                ]
            ),

            ApiResponse(
                responseCode = "409",
                description = "Contact type or value already exists",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "TypeExists",
                                value = ErrorResponseExample.STORE_CONTACT_ALREADY_EXISTS
                            )]
                    )
                ]
            ),

            ApiResponse(
                responseCode = "400",
                description = "Duplicate type in request payload",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "DuplicateTypes",
                                value = ErrorResponseExample.STORE_CONTACT_TYPE_DUPLICATE
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
                        schema = Schema(implementation = ErrorResponse::class),
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "InternalError",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class AddContacts


    @Operation(
        summary = "Update store contacts",
        description = "Update one or multiple contact entries for a store",
        requestBody = RequestBody(
            required = true,
            description = "List of contacts to update",
            content = [
                Content(
                    mediaType = "application/json",
                    schema = Schema(implementation = StoreContactCreateRequest::class),
                    examples = [
                        ExampleObject(
                            name = "UpdateContactsExample",
                            value = """
                            {
                              "contacts": [
                                { "id": "d716b2c4-5bca-4b09-8c32-1dfbb0c4e902", "value": "new@example.com" },
                                { "id": "fd91d26b-e86b-4ef6-82b8-94686bc6f97e", "value": "07899999999" }
                              ]
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
                description = "Contacts updated successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreContactResponse::class)
                    )
                ]
            ),

            ApiResponse(
                responseCode = "404",
                description = "Contact not found",
                content = [
                    Content(
                        schema = Schema(implementation = ErrorResponse::class),
                        mediaType = "application/json",
                        examples = [
                            ExampleObject(
                                name = "ContactNotFound",
                                value = ErrorResponseExample.STORE_CONTACT_NOT_FOUND
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
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalError",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class UpdateContacts

    @Operation(
        summary = "Get store contacts",
        description = "Returns all contacts belonging to the store",
        responses = [
            ApiResponse(
                responseCode = "200",
                description = "Contacts retrieved successfully",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = StoreContactResponse::class),
                        examples = [
                            ExampleObject(
                                name = "GetContactsExample",
                                value = """
                            {
                              "contacts": [
                                {
                                  "id": "d716b2c4-5bca-4b09-8c32-1dfbb0c4e902",
                                  "type": "EMAIL",
                                  "value": "store@example.com"
                                },
                                {
                                  "id": "fd91d26b-e86b-4ef6-82b8-94686bc6f97e",
                                  "type": "PHONE",
                                  "value": "07712345678"
                                }
                              ]
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
                        schema = Schema(implementation = ErrorResponse::class),
                        examples = [
                            ExampleObject(
                                name = "InternalError",
                                value = ErrorResponseExample.INTERNAL_SERVER_ERROR
                            )
                        ]
                    )
                ]
            )
        ]
    )
    annotation class GetContacts

}
