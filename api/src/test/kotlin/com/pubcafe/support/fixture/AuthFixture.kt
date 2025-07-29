package com.pubcafe.support.fixture

import com.pubcafe.api.auth.dto.OAuthTokenInfo
import com.pubcafe.api.auth.dto.OAuthUserInfo
import com.pubcafe.api.common.infra.oauth.google.GoogleTokenInfo
import com.pubcafe.api.common.infra.oauth.google.GoogleUserInfo

object AuthFixture {

    const val FAKE_AUTH_CODE = "4/0AUJR-x4xWQ792pRQa1i2FSjobPsBDLrwI3Im_cLB7lK_AvwyIR4DBiCZwmLIfavV5N25HQ"
    const val FAKE_JWT_TOKEN = ""

    /**
     * mock server json response
     */
    val oAuthTokenInfo: OAuthTokenInfo = GoogleTokenInfo(
        accessToken = "mock_token"
    )

    val oAuthUserInfo: OAuthUserInfo = GoogleUserInfo(
        id = "123456",
        name = "홍길동",
        email = "test@example.com"
    )
}