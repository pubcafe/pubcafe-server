package com.pubcafe.api.common.infra.oauth.google

import com.pubcafe.api.auth.dto.OAuthTokenInfo
import com.pubcafe.api.auth.dto.OAuthUserInfo
import com.pubcafe.api.auth.model.OAuthProvider
import com.pubcafe.api.auth.port.out.OAuthClient
import com.pubcafe.api.common.infra.oauth.exception.OAuthClientException
import com.pubcafe.api.common.model.ResponseCode
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.client.RestClient
import org.springframework.web.client.RestClientException

@Component
class GoogleOAuthClient(
    private val oAuthProperties: GoogleOAuthProperties,
    private val restClient: RestClient
): OAuthClient {

    override fun supports(provider: OAuthProvider) = provider == OAuthProvider.GOOGLE

    override fun requestAccessToken(code: String): OAuthTokenInfo {
        val body = LinkedMultiValueMap<String, String>().apply {
            add("code", code)
            add("client_id", oAuthProperties.credentials.clientId)
            add("client_secret", oAuthProperties.credentials.clientSecret)
            add("redirect_uri", oAuthProperties.credentials.redirectUri)
            add("grant_type", "authorization_code")
        }

        try {
            return restClient.post()
                .uri(oAuthProperties.provider.uri.token)
                .contentType(org.springframework.http.MediaType.APPLICATION_FORM_URLENCODED)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                    throw OAuthClientException(ResponseCode.BAD_REQUEST_ERROR, String(response.body.readAllBytes()))
                }
                .onStatus(HttpStatusCode::is5xxServerError) { _, response ->
                    throw OAuthClientException(ResponseCode.BAD_GATEWAY_ERROR, String(response.body.readAllBytes()))
                }
                .body(GoogleTokenInfo::class.java)
                ?: throw OAuthClientException(ResponseCode.BAD_GATEWAY_ERROR, "Google 액세스 토큰 응답이 비어 있습니다.")
        } catch (e: RestClientException) {
            throw OAuthClientException(ResponseCode.INTERNAL_SERVER_ERROR, "${e.message}")
        }
    }

    override fun requestUserInfo(accessToken: String): OAuthUserInfo {
        try {
            return restClient.get()
                .uri(oAuthProperties.provider.uri.userInfo)
                .headers { it.setBearerAuth(accessToken) }
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError) { _, response ->
                    throw OAuthClientException(ResponseCode.BAD_REQUEST_ERROR, String(response.body.readAllBytes()))
                }
                .onStatus(HttpStatusCode::is5xxServerError) { _, response ->
                    throw OAuthClientException(ResponseCode.BAD_GATEWAY_ERROR, String(response.body.readAllBytes()))
                }
                .body(GoogleUserInfo::class.java)
                ?: throw OAuthClientException(ResponseCode.BAD_GATEWAY_ERROR, "Google 사용자 정보 응답이 비어 있습니다.")
        } catch (e: RestClientException) {
            throw OAuthClientException(ResponseCode.INTERNAL_SERVER_ERROR, "${e.message}")
        }
    }
}