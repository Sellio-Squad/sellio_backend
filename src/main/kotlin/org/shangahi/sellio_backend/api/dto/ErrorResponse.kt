package org.shangahi.sellio_backend.api.dto

import java.time.Instant

data class ErrorResponse(
    val timestamp: Instant = Instant.now(),
    val status: Int,
    val error: String,
    val message: String?,
    val path: String,
    val code: String? = null
)
