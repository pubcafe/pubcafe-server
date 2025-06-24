package com.pubcafe.api.common.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.servers.Server
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SwaggerConfig {

    @Bean
    fun springDocOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(apiInfo())
            .externalDocs(
                ExternalDocumentation()
                    .description("PubCafe Backend Github")
                    .url("https://github.com/pubcafe/pubcafe-server")
            )
            .addServersItem(Server().url("/").description("host"))
            .components(
                Components()
                    .addSecuritySchemes("bearerAuth", bearerAuthScheme())
            )
            .addSecurityItem(SecurityRequirement().addList("bearerAuth"))
    }

    private fun apiInfo(): Info {
        return Info()
            .title("PubCafe API")
            .version("v1.0.0")
    }

    private fun bearerAuthScheme(): SecurityScheme {
        return SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
    }
}