package com.pubcafe.api.auth.model

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties(
    val secretKey: String,
    val accessExpirationTime: Long,
    val refreshExpirationTime: Long
)
