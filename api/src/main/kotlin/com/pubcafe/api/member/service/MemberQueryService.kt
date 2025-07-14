package com.pubcafe.api.member.service

import com.pubcafe.api.common.exception.BusinessException
import com.pubcafe.api.common.model.ResponseCode
import com.pubcafe.api.member.dto.MemberProfileRes
import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.domain.member.MemberProfile
import com.pubcafe.core.repository.MemberRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class MemberQueryService(
    private val memberRepository: MemberRepository
) {

    fun getMemberById(memberId: Long): Member {
        return memberRepository.findById(memberId)
            ?: throw BusinessException(ResponseCode.NOT_FOUND_ERROR, "존재하지 않는 멤버입니다.")
    }

    fun getMemberProfileById(memberId: Long): MemberProfileRes {
        val member: Member = getMemberById(memberId)
        val profile: MemberProfile? = member.profile

        return MemberProfileRes(
            email = member.email,
            alias = profile?.alias,
            displayName = profile?.displayName,
            introduction = profile?.introduction,
            gender = profile?.gender,
            country = profile?.country,
            contact = profile?.contact,
            languages = profile?.languages,
            links = profile?.links,
            profileImage = profile?.profileImage,
            bannerImage = profile?.bannerImage
        )
    }
}