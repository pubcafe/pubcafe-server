package com.pubcafe.api.common.security

import com.pubcafe.api.auth.util.JwtProvider
import com.pubcafe.api.common.security.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtProvider: JwtProvider
) {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { csrf -> csrf.disable() }
            .headers { it.frameOptions { frame -> frame.disable() } }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        "/",
                        // rest docs
                        "/docs/index.html",
                        // auth
                        "/api/auth/**",
                        // h2
                        "/h2-console/**",
                        // swagger
                        "/v3/api-docs/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/webjars/**"
                    ).permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtProvider),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }
}