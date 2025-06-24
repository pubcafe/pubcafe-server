package com.pubcafe.api.common.infra.oauth.google

import com.fasterxml.jackson.annotation.JsonProperty
import com.pubcafe.api.auth.dto.OAuthTokenInfo

data class GoogleTokenInfo(
    @JsonProperty("access_token")
    override val accessToken: String
): OAuthTokenInfo
