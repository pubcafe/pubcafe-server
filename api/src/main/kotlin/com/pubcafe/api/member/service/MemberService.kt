package com.pubcafe.api.member.service

import com.pubcafe.api.auth.util.JwtProvider
import com.pubcafe.api.member.controller.dto.SignUpRes
import com.pubcafe.api.member.dto.SignUpReq
import com.pubcafe.core.domain.member.MemberProfile
import com.pubcafe.core.domain.member.dto.ProfileCreateForm
import com.pubcafe.core.repository.MemberProfileRepository
import com.pubcafe.core.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class MemberService(
    private val memberQueryService: MemberQueryService,
    private val memberValidationService: MemberValidationService,
    private val jwtProvider: JwtProvider,
    private val memberRepository: MemberRepository,
    private val memberProfileRepository: MemberProfileRepository
) {
    fun signUp(memberId: Long, req: SignUpReq): SignUpRes {
        val member = memberQueryService.getMemberById(memberId)

        // 검증
        memberValidationService.validateNewMember(member)
        memberValidationService.validateUniqueAlias(req.alias)

        // 프로필 저장
        val form = ProfileCreateForm(
            alias = req.alias,
            displayName = req.displayName,
            introduction = req.introduction,
            country = req.countryCode,
            gender = req.gender,
            contact = req.contact,
            languages = req.languages,
            links = req.links,
            profileImage = req.profileImage,
            bannerImage = req.bannerImage
        )

        val profile: MemberProfile = MemberProfile.createFor(member, form)
        memberProfileRepository.save(profile)

        // 일반 유저로 role 변경
        member.convertToMember()
        memberRepository.save(member)

        // 새로운 access token 반환
        val newAccessToken = jwtProvider.createAccessToken(member.id!!, member.role);
        return SignUpRes(newAccessToken)
    }
}