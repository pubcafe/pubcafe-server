package com.pubcafe.support.helper

import com.pubcafe.core.domain.member.MemberRole
import org.springframework.security.test.context.support.WithSecurityContext
import kotlin.annotation.AnnotationRetention.RUNTIME
import kotlin.annotation.AnnotationTarget.*

@WithSecurityContext(factory = WithMockLoginUserSecurityContextFactory::class)
@Target(FUNCTION, CLASS)
@Retention(RUNTIME)
annotation class WithMockLoginMember(val id: Long = 1L, val name: String = "테스트 로그인 멤버", val role: MemberRole = MemberRole.MEMBER)