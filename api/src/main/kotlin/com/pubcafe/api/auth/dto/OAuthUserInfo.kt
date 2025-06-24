package com.pubcafe.api.auth.dto

import com.pubcafe.api.auth.model.OAuthProvider

interface OAuthUserInfo {
    val provider: OAuthProvider
    val id: String
    val email: String
    val name: String
}
