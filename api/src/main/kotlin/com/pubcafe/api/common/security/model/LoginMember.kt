package com.pubcafe.api.common.security.model

import com.pubcafe.core.domain.member.MemberRole

data class LoginMember(
    val memberId: Long,
    val role: MemberRole
)