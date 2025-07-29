package com.pubcafe.support.fixture

import com.pubcafe.api.member.dto.SignUpReq
import com.pubcafe.core.domain.common.CountryCode
import com.pubcafe.core.domain.common.Gender
import com.pubcafe.core.domain.common.LanguageCode

object MemberFixture {

    val signUpReq_required: SignUpReq = SignUpReq(
        alias = "unique_alias",
        displayName = "pubcafe",
        gender = Gender.MALE,
        introduction = null,
        countryCode = CountryCode.KR,
        contact = "+821012345678",
        languages = listOf(LanguageCode.KO),
        links = null,
        profileImage = null,
        bannerImage = null
    )

    val signUpReq_all: SignUpReq = SignUpReq(
        alias = "unique_alias",
        displayName = "pubcafe",
        introduction = "안녕하세요, 저는 pubcafe 입니다.",
        gender = Gender.FEMALE,
        countryCode = CountryCode.US,
        contact = "+12025550123",
        languages = listOf(LanguageCode.EN, LanguageCode.KO),
        links = listOf("https://github.com/username", "https://linkedin.com/in/username"),
        profileImage = "https://cdn.example.com/profile.jpg",
        bannerImage = "https://cdn.example.com/banner.jpg"
    )
}