package com.pubcafe.api.auth.controller.dto

data class LoginRes(
    val accessToken: String,
    val isSignedUp: Boolean
)
