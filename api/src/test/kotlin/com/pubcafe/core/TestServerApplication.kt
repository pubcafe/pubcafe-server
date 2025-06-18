package com.pubcafe.core

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<ServerApplication>().with(TestcontainersConfiguration::class).run(*args)
}
