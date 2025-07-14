package com.pubcafe.api.common.security.filter

import com.pubcafe.api.auth.util.JwtProvider
import com.pubcafe.api.common.constant.AuthConstants
import com.pubcafe.api.common.security.model.LoginMember
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthenticationFilter(
    private val jwtProvider: JwtProvider
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = resolveToken(request)

        if (token != null) {
            try {
                jwtProvider.validateToken(token)

                val memberId = jwtProvider.getMemberId(token)
                val role = jwtProvider.getMemberRole(token)
                val loginMember = LoginMember(memberId, role)

                val auth = UsernamePasswordAuthenticationToken(
                    loginMember,
                    null,
                    listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
                )

                SecurityContextHolder.getContext().authentication = auth
            } catch (e: Exception) {
                // TODO: 시큐리티 예외 추가하기
                throw e
            }
        }

        filterChain.doFilter(request, response)
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val bearer = request.getHeader(AuthConstants.AUTH_HEADER) ?: return null
        return if (bearer.startsWith(AuthConstants.BEARER_PREFIX)) bearer.substring(7) else null
    }
}