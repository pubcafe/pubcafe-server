package com.pubcafe.api.auth.util

import com.pubcafe.api.auth.exception.ExpiredJwtTokenException
import com.pubcafe.api.auth.exception.InvalidJwtTokenException
import com.pubcafe.api.auth.model.JwtProperties
import com.pubcafe.core.entity.MemberRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Component
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtProvider(
    val jwtProperties: JwtProperties
) {
    private val secretKey: SecretKey = Keys.hmacShaKeyFor(jwtProperties.secretKey.toByteArray())

    fun createAccessToken(memberId: Long, role: MemberRole): String {
        val now = Date()
        val expiration = Date(now.time + jwtProperties.accessExpirationTime)

        return Jwts.builder()
            .subject(memberId.toString())
            .claim("role", role.toString())
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    fun createRefreshToken(memberId: Long): String {
        val now = Date()
        val expiration = Date(now.time + jwtProperties.refreshExpirationTime)

        return Jwts.builder()
            .subject(memberId.toString())
            .issuedAt(now)
            .expiration(expiration)
            .signWith(secretKey)
            .compact()
    }

    private fun parseClaims(token: String): Jws<Claims> {
        return try {
            Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
        } catch (e: io.jsonwebtoken.ExpiredJwtException) {
            throw ExpiredJwtTokenException()
        } catch (e: Exception) {
            throw InvalidJwtTokenException()
        }
    }

    fun validateToken(token: String) {
        parseClaims(token)
    }

    fun getMemberId(token: String): Long {
        return parseClaims(token).payload.subject.toLong()
    }
}
