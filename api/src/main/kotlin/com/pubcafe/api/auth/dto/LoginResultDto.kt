package com.pubcafe.api.auth.dto

data class LoginResultDto(
    val accessToken: String,
    val refreshToken: String,
    val isSignedUp: Boolean
)
