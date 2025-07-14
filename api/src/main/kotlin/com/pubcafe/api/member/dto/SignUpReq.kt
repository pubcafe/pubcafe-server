package com.pubcafe.api.member.dto

import com.pubcafe.core.domain.common.CountryCode
import com.pubcafe.core.domain.common.Gender
import com.pubcafe.core.domain.common.LanguageCode
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class SignUpReq(

    @Schema(nullable = false)
    @NotBlank(message = "고유 이름은 비어있을 수 없습니다.")
    val alias: String,

    @Schema(nullable = false)
    @NotBlank(message = "표시 이름은 비어있을 수 없습니다.")
    val displayName: String,

    val introduction: String?,

    @Schema(nullable = false)
    @NotNull(message = "성별은 비어있을 수 없습니다.")
    val gender: Gender,

    @Schema(nullable = false)
    @NotNull(message = "국가 코드는 비어있을 수 없습니다.")
    val countryCode: CountryCode,

    @Schema(nullable = false)
    @NotNull(message = "연락처는 비어있을 수 없습니다.")
    val contact: String,

    @Schema(nullable = false)
    @NotEmpty(message = "사용 언어는 비어있을 수 없습니다.")
    val languages: List<LanguageCode>,

    val links: List<String>?,

    val profileImage: String?,

    val bannerImage: String?
)
