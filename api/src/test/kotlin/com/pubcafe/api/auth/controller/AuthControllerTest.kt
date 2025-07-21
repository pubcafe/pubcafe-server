package com.pubcafe.api.auth.controller

import com.pubcafe.api.auth.model.OAuthProvider
import com.pubcafe.api.common.constant.AuthConstants
import com.pubcafe.support.fixture.AuthFixture
import com.pubcafe.support.template.IntegrationTest
import okhttp3.mockwebserver.MockResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.test.web.servlet.post

class AuthControllerTest : IntegrationTest() {

    @BeforeEach
    fun setup() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(toJson(AuthFixture.oAuthTokenInfo))
                .setHeader("Content-Type", "application/json")
        )
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(toJson(AuthFixture.oAuthUserInfo))
                .setHeader("Content-Type", "application/json")
        )
    }

    @Nested
    @DisplayName("Auth: 로그인 테스트")
    inner class Login {

        private val uri: String = "/api/auth/login"

        @DisplayName("[성공] 새로운 멤버가 구글 로그인에 성공한다.")
        @Test
        fun google_login_success_new_member() {
            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.OAUTH_PROVIDER_HEADER, OAuthProvider.GOOGLE)
                    header(AuthConstants.OAUTH_CODE_HEADER, AuthFixture.FAKE_AUTH_CODE)
                }
            }
                .andExpect { status { isOk() } }
                .andDo {
                    handle(
                        document(
                            "auth_login/success/google_login_new_member",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.OAUTH_PROVIDER_HEADER).description("OAuth Provider"),
                                headerWithName(AuthConstants.OAUTH_CODE_HEADER).description("Auth Code")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터"),
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING).description("JWT access token"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING).description("멤버 역할 (`NEW_MEMBER`, `MEMBER`, `ADMIN`")
                            )
                        )
                    )
                }
        }
    }
}