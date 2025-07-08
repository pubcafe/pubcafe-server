package com.pubcafe.api.auth.service;

import com.pubcafe.api.auth.dto.LoginResultDto
import com.pubcafe.api.auth.exception.UnsupportedOAuthProviderException
import com.pubcafe.api.auth.port.out.OAuthClient
import com.pubcafe.core.domain.member.Member
import com.pubcafe.core.domain.member.MemberRole

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

        // oauth 서버에서 정보 조회
        val tokenInfo = client.requestAccessToken(code)
        val userInfo = client.requestUserInfo(tokenInfo.accessToken)

        // 존재하지 않으면 새 멤버 생성
        val member = memberRepository.findByEmail(userInfo.email)
            ?: memberRepository.save(Member.create(userInfo.email))
        val memberId: Long = requireNotNull(member.id)

        val accessToken = jwtProvider.createAccessToken(memberId, member.role)
        val refreshToken = jwtProvider.createRefreshToken(memberId)

        return LoginResultDto(accessToken, refreshToken, member.role != MemberRole.NEW_MEMBER)
    }
}
