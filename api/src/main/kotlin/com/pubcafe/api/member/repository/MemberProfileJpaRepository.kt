package com.pubcafe.api.member.repository

import com.pubcafe.core.domain.member.MemberProfile
import org.springframework.data.jpa.repository.JpaRepository

interface MemberProfileJpaRepository : JpaRepository<MemberProfile, Long> {

    fun getMemberProfileByMember_Id(memberId: Long): MemberProfile?
}