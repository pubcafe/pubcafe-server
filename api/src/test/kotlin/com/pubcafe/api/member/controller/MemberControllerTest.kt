package com.pubcafe.api.member.controller

import com.pubcafe.api.common.constant.AuthConstants
import com.pubcafe.core.domain.member.MemberRole
import com.pubcafe.support.fixture.AuthFixture
import com.pubcafe.support.fixture.MemberFixture
import com.pubcafe.support.helper.WithMockLoginMember
import com.pubcafe.support.template.IntegrationTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName
import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.post

class MemberControllerTest : IntegrationTest() {

    @Nested
    @DisplayName("내 프로필 정보 조회 API 테스트")
    inner class GetMyProfileInfo {

        private val uri: String = "/api/members/me"

        @WithMockLoginMember(id = 100)
        @DisplayName("[성공] 새로운 멤버의 내 프로필 정보 조회에 성공한다.")
        @Test
        fun success_get_new_member_profile_info() {
            mockMvc.get(uri) {
                headers {
                    header(AuthConstants.AUTH_HEADER, AuthFixture.FAKE_JWT_TOKEN)
                }
            }
                .andExpect { status { isOk() } }
                .andDo {
                    handle(
                        document(
                            "member/get_my_profile_info/success/get_new_member_profile_info",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.AUTH_HEADER).description("JWT access token")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("멤버 프로필"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.alias").type(JsonFieldType.STRING).description("별명").optional(),
                                fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("표시 이름").optional(),
                                fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("자기소개").optional(),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별 (`MALE`, `FEMALE`)").optional(),
                                fieldWithPath("data.country").type(JsonFieldType.STRING).description("국가 코드 (예: `KR`, `US`)").optional(),
                                fieldWithPath("data.contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                fieldWithPath("data.languages").type(JsonFieldType.ARRAY).description("사용 언어 코드 목록").optional(),
                                fieldWithPath("data.links").type(JsonFieldType.ARRAY).description("외부 링크 목록").optional(),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL").optional(),
                                fieldWithPath("data.bannerImage").type(JsonFieldType.STRING).description("배너 이미지 URL").optional()
                            )
                        )
                    )
                }
        }

        @WithMockLoginMember(id = 101)
        @DisplayName("[성공] 멤버의 내 프로필 정보 조회에 성공한다.")
        @Test
        fun success_get_my_profile_info() {
            mockMvc.get(uri) {
                headers {
                    header(AuthConstants.AUTH_HEADER, AuthFixture.FAKE_JWT_TOKEN)
                }
            }
                .andExpect { status { isOk() } }
                .andDo {
                    handle(
                        document(
                            "member/get_my_profile_info/success/get_logined_member_profile_info",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.AUTH_HEADER).description("JWT access token")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("멤버 프로필"),
                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("data.alias").type(JsonFieldType.STRING).description("별명").optional(),
                                fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("표시 이름").optional(),
                                fieldWithPath("data.introduction").type(JsonFieldType.STRING).description("자기소개").optional(),
                                fieldWithPath("data.gender").type(JsonFieldType.STRING).description("성별 (`MALE`, `FEMALE`)").optional(),
                                fieldWithPath("data.country").type(JsonFieldType.STRING).description("국가 코드 (예: `KR`, `US`)").optional(),
                                fieldWithPath("data.contact").type(JsonFieldType.STRING).description("연락처").optional(),
                                fieldWithPath("data.languages").type(JsonFieldType.ARRAY).description("사용 언어 코드 목록").optional(),
                                fieldWithPath("data.links").type(JsonFieldType.ARRAY).description("외부 링크 목록").optional(),
                                fieldWithPath("data.profileImage").type(JsonFieldType.STRING).description("프로필 이미지 URL").optional(),
                                fieldWithPath("data.bannerImage").type(JsonFieldType.STRING).description("배너 이미지 URL").optional()
                            )
                        )
                    )
                }
        }
    }

    @Nested
    @DisplayName("회원가입 API 테스트")
    inner class SignUp {

        private val uri: String = "/api/members/signup"

        @Test
        @WithMockLoginMember(id=100, role=MemberRole.NEW_MEMBER)
        @DisplayName("[성공] 새로운 멤버가 최소한의 정보 입력으로 회원가입에 성공한다.")
        fun success_new_member_signup_required_info() {
            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.AUTH_HEADER, AuthFixture.FAKE_JWT_TOKEN)
                    contentType = MediaType.APPLICATION_JSON
                }
                content = toJson(MemberFixture.signUpReq_required)
            }
                .andExpect { status { isCreated() } }
                .andDo {
                    handle(
                        document(
                            "member/signup/success/new_member_required_info",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.AUTH_HEADER).description("JWT access token")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터"),
                                fieldWithPath("data.newAccessToken").type(JsonFieldType.VARIES).description("새로운 access token")
                            )
                        )
                    )
                }
        }

        @Test
        @WithMockLoginMember(id=100, role=MemberRole.NEW_MEMBER)
        @DisplayName("[성공] 새로운 멤버가 모든 정보 입력으로 회원가입에 성공한다.")
        fun success_new_member_signup_all_info() {
            mockMvc.post(uri) {
                headers {
                    header(AuthConstants.AUTH_HEADER, AuthFixture.FAKE_JWT_TOKEN)
                    contentType = MediaType.APPLICATION_JSON
                }
                content = toJson(MemberFixture.signUpReq_all)
            }
                .andExpect { status { isCreated() } }
                .andDo {
                    handle(
                        document(
                            "member/signup/success/new_member_all_info",

                            preprocessRequest(prettyPrint()),
                            preprocessResponse(prettyPrint()),

                            requestHeaders(
                                headerWithName(AuthConstants.AUTH_HEADER).description("JWT access token")
                            ),
                            responseFields(
                                fieldWithPath("code").type(JsonFieldType.STRING).description("응답 코드"),
                                fieldWithPath("message").type(JsonFieldType.STRING).description("응답 메시지"),
                                fieldWithPath("data").type(JsonFieldType.VARIES).description("응답 데이터"),
                                fieldWithPath("data.newAccessToken").type(JsonFieldType.VARIES).description("새로운 access token")
                            )
                        )
                    )
                }
        }
    }
}