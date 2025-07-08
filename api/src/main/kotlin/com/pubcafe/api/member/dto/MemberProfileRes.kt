package com.pubcafe.api.member.dto

import com.pubcafe.core.domain.common.CountryCode
import com.pubcafe.core.domain.common.LanguageCode

data class MemberProfileRes(
    val email: String,
    val alias: String?,
    val displayName: String?,
    val introduction: String?,
    val country: CountryCode?,
    val contact: String?,
    val languages: List<LanguageCode>?,
    val links: List<String>?,
    val profileImage: String?,
    val bannerImage: String?
)