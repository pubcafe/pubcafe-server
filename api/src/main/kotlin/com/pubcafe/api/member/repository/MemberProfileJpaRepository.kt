package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.domain.member.MemberProfile
import org.springframework.data.jpa.repository.JpaRepository

interface MemberProfileJpaRepository : JpaRepository<MemberProfile, Long> {

    fun existsByAlias(alias: String): Boolean

    fun existsByMember(member: Member): Boolean
}