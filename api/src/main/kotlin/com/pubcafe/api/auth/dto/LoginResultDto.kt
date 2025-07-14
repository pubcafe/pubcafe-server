package com.pubcafe.api.auth.dto

import com.pubcafe.core.domain.member.MemberRole

data class LoginResultDto(
    val accessToken: String,
    val refreshToken: String,
    val role: MemberRole
)
