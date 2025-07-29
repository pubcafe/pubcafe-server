rootProject.name = "server"
include(":core", ":api")

pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }

    val kotlinVersion = "2.1.0"
    val springBootVersion = "3.5.0"

    plugins {
        id("org.jetbrains.kotlin.jvm") version kotlinVersion
        id("org.jetbrains.kotlin.kapt") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.spring") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.jpa") version kotlinVersion
        id("org.jetbrains.kotlin.plugin.allopen") version kotlinVersion

        id("org.springframework.boot") version springBootVersion
        id("io.spring.dependency-management") version "1.1.7"
    }
}