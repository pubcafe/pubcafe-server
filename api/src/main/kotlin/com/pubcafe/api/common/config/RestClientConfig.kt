package com.pubcafe.api.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestClient

@Configuration
class RestClientConfig {

    // TODO: Rest client 추가 설정
    @Bean
    fun restClient(): RestClient {
        return RestClient.create()
    }
}
