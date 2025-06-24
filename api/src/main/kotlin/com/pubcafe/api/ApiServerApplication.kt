package com.pubcafe.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
@ConfigurationPropertiesScan
@EntityScan(basePackages = ["com.pubcafe.core.entity"])
class ServerApplication

fun main(args: Array<String>) {
	runApplication<ServerApplication>(*args)
}
