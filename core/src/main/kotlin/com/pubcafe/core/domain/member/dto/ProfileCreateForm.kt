package com.pubcafe.core.domain.member.dto

import com.pubcafe.core.domain.common.CountryCode
import com.pubcafe.core.domain.common.LanguageCode

data class ProfileCreateForm(

    val alias: String,
    val displayName: String,
    val introduction: String?,
    val country: CountryCode,
    val contact: String?,
    val languages: List<LanguageCode> = emptyList(),
    val links: List<String> = emptyList(),
    val profileImage: String?,
    val bannerImage: String?
)
