package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface MemberJpaRepository : JpaRepository<Member, Long> {

    fun findMemberByEmail(email: String): Member?
}