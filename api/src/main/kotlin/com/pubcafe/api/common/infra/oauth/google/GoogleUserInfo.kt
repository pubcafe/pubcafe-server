package com.pubcafe.api.common.infra.oauth.google

import com.pubcafe.api.auth.dto.OAuthUserInfo
import com.pubcafe.api.auth.model.OAuthProvider

data class GoogleUserInfo(
    override val name: String,
    override val email: String,
    override val id: String
) : OAuthUserInfo {
    override val provider = OAuthProvider.GOOGLE
}