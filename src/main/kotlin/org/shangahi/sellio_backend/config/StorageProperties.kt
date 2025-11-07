package org.shangahi.sellio_backend.config

import org.springframework.boot.context.properties.ConfigurationProperties


@ConfigurationProperties(prefix = "storage")
data class StorageProperties(
    val key: String,
    val secret: String,
    val endpoint: String,
    val bucket: String,
    val cdnEndpoint: String
)