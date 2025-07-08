package com.pubcafe.core.repository

import com.pubcafe.core.domain.member.MemberProfile

interface MemberProfileRepository {

    fun save(memberProfile: MemberProfile): MemberProfile
}