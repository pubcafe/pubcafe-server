package com.pubcafe.core.repository

import com.pubcafe.core.domain.member.Member

interface MemberRepository {

    fun save(member: Member): Member
    fun findById(id: Long): Member?
    fun findByEmail(email: String): Member?
}