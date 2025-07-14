package com.pubcafe.core.repository

import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.domain.member.MemberProfile

interface MemberProfileRepository {

    fun save(memberProfile: MemberProfile): MemberProfile

    fun isExistAlias(alias: String): Boolean

    fun isExistByMember(member: Member): Boolean
}