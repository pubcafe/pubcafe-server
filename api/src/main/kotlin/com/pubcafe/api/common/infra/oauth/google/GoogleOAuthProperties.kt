package com.pubcafe.api.common.infra.oauth.google

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "oauth.client.google")
data class GoogleOAuthProperties(
    val credentials: Credentials,
    val provider: Provider
) {
    data class Credentials(
        val clientId: String,
        val clientSecret: String,
        val redirectUri: String
    )

    data class Provider(
        val uri: Uri
    ) {
        data class Uri(
            val userInfo: String,
            val token: String
        )
    }
}
