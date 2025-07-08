package com.pubcafe.api.auth.controller

import com.pubcafe.api.auth.controller.dto.LoginRes
import com.pubcafe.api.auth.dto.LoginResultDto
import com.pubcafe.api.auth.model.OAuthProvider
import com.pubcafe.api.auth.service.OAuthService
import com.pubcafe.api.auth.util.JwtCookieProvider
import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.common.model.ResponseForm
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "auth", description = "인증")
@RequestMapping("/api/auth")
@RestController
class AuthController(
    private val oAuthService: OAuthService
) {

    @Operation(summary = "OAuth 로그인")
    @PostMapping("/login")
    fun login(
        @RequestHeader("X-OAUTH-PROVIDER") provider: OAuthProvider,
        @RequestHeader("X-OAUTH-CODE") code: String
    ): ResponseEntity<ResponseForm<LoginRes>> {
        val result: LoginResultDto = oAuthService.login(provider, code)
        val response = LoginRes(result.accessToken, result.isSignedUp)
        val cookie: ResponseCookie = JwtCookieProvider.createRefreshTokenCookie(result.refreshToken)

        return ResponseEntity.ok()
            .header(HttpHeaders.SET_COOKIE, cookie.toString())
            .body(ResponseForm.success(ResponseCode.LOGIN_SUCCESS, response))
    }
}