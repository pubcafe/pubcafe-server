package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.MemberProfile
import com.pubcafe.core.repository.MemberProfileRepository
import org.springframework.stereotype.Repository

@Repository
class MemberProfileRepositoryImpl(
    private val memberProfileJpaRepository: MemberProfileJpaRepository
) : MemberProfileRepository {

    override fun save(memberProfile: MemberProfile): MemberProfile {
        return memberProfileJpaRepository.save(memberProfile)
    }
}