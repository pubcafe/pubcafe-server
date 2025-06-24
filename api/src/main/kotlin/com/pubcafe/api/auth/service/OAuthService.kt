package com.pubcafe.api.auth.service;

import com.pubcafe.api.auth.dto.LoginResultDto
import com.pubcafe.api.auth.exception.UnsupportedOAuthProviderException
import com.pubcafe.api.auth.port.out.OAuthClient
import com.pubcafe.core.entity.Member
import com.pubcafe.core.entity.MemberRole

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pubcafe.api.auth.model.OAuthProvider;
import com.pubcafe.api.auth.util.JwtProvider;
import com.pubcafe.core.repository.MemberRepository;

@Transactional
@Service
class OAuthService(
    private val clients: List<OAuthClient>,
    private val memberRepository:MemberRepository,
    private val jwtProvider:JwtProvider
) {

    fun login(provider: OAuthProvider, code: String): LoginResultDto {
        val client = clients.find { it.supports(provider) }
            ?: throw UnsupportedOAuthProviderException()

        val tokenInfo = client.requestAccessToken(code)
        val userInfo = client.requestUserInfo(tokenInfo.accessToken)

        val member = memberRepository.findByEmail(userInfo.email)
            ?: memberRepository.save(Member.createNew(userInfo.email))
        val memberId: Long = requireNotNull(member.id)

        val accessToken = jwtProvider.createAccessToken(memberId, member.role)
        val refreshToken = jwtProvider.createRefreshToken(memberId)

        return LoginResultDto(accessToken, refreshToken, member.role != MemberRole.NEW_MEMBER)
    }
}
