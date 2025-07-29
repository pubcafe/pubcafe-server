package com.pubcafe.support.helper

import com.pubcafe.api.common.security.model.LoginMember
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.test.context.support.WithSecurityContextFactory

class WithMockLoginUserSecurityContextFactory : WithSecurityContextFactory<WithMockLoginMember> {

    override fun createSecurityContext(annotation: WithMockLoginMember): SecurityContext {
        val context = SecurityContextHolder.createEmptyContext()

        val memberId = annotation.id
        val role = annotation.role

        val loginMember = LoginMember(memberId, role)

        val auth = UsernamePasswordAuthenticationToken(
            loginMember,
            null,
            listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
        )

        context.authentication = auth
        return context
    }
}
