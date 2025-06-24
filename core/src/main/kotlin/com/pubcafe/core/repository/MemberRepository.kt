package com.pubcafe.core.repository

import com.pubcafe.core.entity.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun findByEmail(email: String): Member?
}