package com.pubcafe.api.auth.controller.dto

import com.pubcafe.core.domain.member.MemberRole

data class LoginRes(
    val accessToken: String,
    val role: MemberRole
)
