package com.pubcafe.api.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .headers { it.frameOptions { frame -> frame.disable() } }
            .authorizeHttpRequests { requests ->
                requests
                    .requestMatchers(
                        "/",
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

        return http.build()
    }
}