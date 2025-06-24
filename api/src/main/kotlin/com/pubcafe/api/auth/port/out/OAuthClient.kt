package com.pubcafe.api.auth.port.out

import com.pubcafe.api.auth.dto.OAuthTokenInfo
import com.pubcafe.api.auth.dto.OAuthUserInfo
import com.pubcafe.api.auth.model.OAuthProvider

interface OAuthClient {
    fun supports(provider: OAuthProvider): Boolean
    fun requestAccessToken(code: String): OAuthTokenInfo
    fun requestUserInfo(accessToken: String): OAuthUserInfo
}
