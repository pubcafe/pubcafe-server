package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.Member
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

    override fun isExistAlias(alias: String): Boolean {
        return memberProfileJpaRepository.existsByAlias(alias)
    }

    override fun isExistByMember(member: Member): Boolean {
        return memberProfileJpaRepository.existsByMember(member)
    }
}