package com.pubcafe.api.member.controller

import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.common.model.ResponseForm
import com.pubcafe.api.common.security.model.LoginMember
import com.pubcafe.api.member.dto.MemberProfileRes
import com.pubcafe.api.member.service.MemberQueryService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "member", description = "회원")
@RequestMapping("/api/members")
@RestController
class MemberController(
    private val memberQueryService: MemberQueryService
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
}