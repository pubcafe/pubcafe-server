plugins {
	kotlin("jvm")
	kotlin("kapt") apply false
	kotlin("plugin.spring") apply false
	kotlin("plugin.jpa") apply false
	kotlin("plugin.allopen") apply false

	id("java")
	id("org.springframework.boot") apply false
	id("io.spring.dependency-management")
}

allprojects {
	group = "com.pubcafe"
	version = "0.0.1-SNAPSHOT"

	repositories {
		gradlePluginPortal()
		mavenCentral()
	}
}

subprojects {
	apply(plugin = "org.jetbrains.kotlin.jvm")
	apply(plugin = "io.spring.dependency-management")

	java {
		toolchain {
			languageVersion.set(JavaLanguageVersion.of(21))
		}
	}

	kotlin {
		jvmToolchain(21)
	}

	configurations {
		compileOnly {
			extendsFrom(configurations.annotationProcessor.get())
		}
	}

	tasks.withType<Test> {
		useJUnitPlatform()
	}

	dependencyManagement {
		imports {
			mavenBom("org.springframework.boot:spring-boot-dependencies:3.5.0")
		}
	}
}