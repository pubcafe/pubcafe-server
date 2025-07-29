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

    private val uri: String = "/api/auth/login"

    @Nested
    @DisplayName("Google 로그인 API 테스트")
    inner class LoginGoogle {

        @DisplayName("[성공] 새로운 멤버가 구글 로그인에 성공한다.")
        @Test
        fun success_google_login_new_member() {
            setSuccessResponse()

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
                            "auth/login/success/google_login_new_member",

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
                                fieldWithPath("data.accessToken").type(JsonFieldType.STRING)
                                    .description("JWT access token"),
                                fieldWithPath("data.role").type(JsonFieldType.STRING)
                                    .description("멤버 역할 (`NEW_MEMBER`, `MEMBER`, `ADMIN`")
                            )
                        )
                    )
                }
        }

        @DisplayName("[실패] 지원하지 않는 OAuth 공급자로 요청하면 로그인에 실패한다.")
        @Test
        fun fail_login_unsupported_provider() {
            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.OAUTH_PROVIDER_HEADER, "UNSUPPORTED_PROVIDER")
                    header(AuthConstants.OAUTH_CODE_HEADER, AuthFixture.FAKE_AUTH_CODE)
                }
            }
                .andExpect { status { isBadRequest() } }
                .andDo {
                    handle(
                        document(
                            "auth/login/fail/unsupported_provider",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.OAUTH_PROVIDER_HEADER).description("OAuth Provider"),
                                headerWithName(AuthConstants.OAUTH_CODE_HEADER).description("Auth Code")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터")
                            )
                        )
                    )
                }
        }

        @DisplayName("[실패] 인증 요청 정보가 유효하지 않으면 로그인에 실패한다.")
        @Test
        fun fail_login_invalid_request() {
            setInvalidRequestErrorResponse()

            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.OAUTH_PROVIDER_HEADER, OAuthProvider.GOOGLE)
                    header(AuthConstants.OAUTH_CODE_HEADER, AuthFixture.FAKE_AUTH_CODE)
                }
            }
                .andExpect { status { isBadRequest() } }
                .andDo {
                    handle(
                        document(
                            "auth/login/fail/invalid_request",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.OAUTH_PROVIDER_HEADER).description("OAuth Provider"),
                                headerWithName(AuthConstants.OAUTH_CODE_HEADER).description("Auth Code")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터")
                            )
                        )
                    )
                }
        }

        @DisplayName("[실패] 구글 서버에서 응답이 오지 않으면 로그인에 실패한다.")
        @Test
        fun fail_login_bad_gateway() {
            setBadGateWayErrorResponse()

            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.OAUTH_PROVIDER_HEADER, OAuthProvider.GOOGLE)
                    header(AuthConstants.OAUTH_CODE_HEADER, AuthFixture.FAKE_AUTH_CODE)
                }
            }
                .andExpect { status { isBadGateway() } }
                .andDo {
                    handle(
                        document(
                            "auth/login/fail/bad_gateway",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.OAUTH_PROVIDER_HEADER).description("OAuth Provider"),
                                headerWithName(AuthConstants.OAUTH_CODE_HEADER).description("Auth Code")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터")
                            )
                        )
                    )
                }
        }
    }

    private fun setSuccessResponse() {
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

    private fun setInvalidRequestErrorResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(400)
                .setBody("""{"error": "invalid_grant"}""")
                .setHeader("Content-Type", "application/json")
        )
    }

    private fun setBadGateWayErrorResponse() {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(502)
                .setHeader("Content-Type", "application/json")
        )
    }
}