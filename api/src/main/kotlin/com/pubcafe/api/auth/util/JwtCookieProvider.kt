package com.pubcafe.api.auth.util

import org.springframework.http.ResponseCookie
import java.time.Duration

object JwtCookieProvider {

    fun createRefreshTokenCookie(token: String): ResponseCookie {
        return ResponseCookie.from("refresh-token", token)
            .httpOnly(true)
            .secure(true)
            .path("/api/auth/refresh")
            .maxAge(Duration.ofDays(7))
            .sameSite("Strict")
            .build()
    }
}