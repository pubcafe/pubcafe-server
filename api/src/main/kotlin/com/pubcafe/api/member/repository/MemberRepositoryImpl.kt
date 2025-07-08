package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.repository.MemberRepository
import org.springframework.stereotype.Repository

@Repository
class MemberRepositoryImpl(
    private val memberJpaRepository: MemberJpaRepository
) : MemberRepository {

    override fun save(member: Member): Member {
        return memberJpaRepository.save(member)
    }

    override fun findById(id: Long): Member? {
        return memberJpaRepository.findById(id).orElse(null)
    }

    override fun findByEmail(email: String): Member? {
        return memberJpaRepository.findMemberByEmail(email)
    }
}