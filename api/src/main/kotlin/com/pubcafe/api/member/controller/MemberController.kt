package com.pubcafe.api.member.controller

import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.common.model.ResponseForm
import com.pubcafe.api.common.security.model.LoginMember
import com.pubcafe.api.member.controller.dto.SignUpRes
import com.pubcafe.api.member.dto.MemberProfileRes
import com.pubcafe.api.member.dto.SignUpReq
import com.pubcafe.api.member.service.MemberQueryService
import com.pubcafe.api.member.service.MemberService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@Tag(name = "member", description = "회원")
@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberQueryService: MemberQueryService,
    private val memberService: MemberService
) {

    @Operation(summary = "내 프로필 정보 조회")
    @GetMapping("/me")
    fun getMyProfile(
        @AuthenticationPrincipal loginMember: LoginMember
    ): ResponseEntity<ResponseForm<MemberProfileRes>> {
        val response: MemberProfileRes = memberQueryService.getMemberProfileById(loginMember.memberId)

        return ResponseEntity.ok()
            .body(ResponseForm.success(ResponseCode.GET_SUCCESS, response))
    }

    @PreAuthorize("hasRole('NEW_MEMBER')")
    @Operation(summary = "회원 가입")
    @PostMapping("/signup")
    fun signUp(
        @AuthenticationPrincipal loginMember: LoginMember,
        @RequestBody signUpReq: SignUpReq
    ): ResponseEntity<ResponseForm<SignUpRes>> {
        val response = memberService.signUp(loginMember.memberId, signUpReq);

        return ResponseEntity.ok()
            .body(ResponseForm.success(ResponseCode.SIGN_UP_SUCCESS, response))
    }
}