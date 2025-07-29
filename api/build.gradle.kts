plugins {
	kotlin("jvm")
	kotlin("kapt")
	kotlin("plugin.jpa")
	kotlin("plugin.spring")

	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.asciidoctor.jvm.convert") version "3.3.2"
}

val asciidoctorExt: Configuration by configurations.creating
extra["snippetsDir"] = file("build/generated-snippets")

dependencies {
	// module
	implementation(project(":core"))

	// spring boot
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// kotlin
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
	implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

	// db
	runtimeOnly("com.h2database:h2")
	runtimeOnly("org.postgresql:postgresql")
	implementation("org.flywaydb:flyway-core")
	implementation("org.flywaydb:flyway-database-postgresql")

	// test
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.boot:spring-boot-testcontainers")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test")
	testImplementation("org.springframework.security:spring-security-test")
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// rest docs
	testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
	asciidoctorExt("org.springframework.restdocs:spring-restdocs-asciidoctor")

	// annotation processor
	compileOnly("org.projectlombok:lombok")
	kapt("org.projectlombok:lombok")
	kapt("org.springframework.boot:spring-boot-configuration-processor")

	/*
		Not managed by Spring BOM
	 */

	// jwt
	implementation ("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly ("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly ("io.jsonwebtoken:jjwt-jackson:0.12.6")

	// querydsl
	implementation("com.querydsl:querydsl-jpa:5.1.0:jakarta")

	// docs
	implementation ("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.9")

	// Mock Server
	testImplementation ("com.squareup.okhttp3:mockwebserver:5.0.0")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

tasks.test {
	outputs.dir(project.extra["snippetsDir"]!!)		// snippet 경로 지정
}

tasks.asciidoctor {
	dependsOn(tasks.test)							// test task를 선행
	inputs.dir(project.extra["snippetsDir"]!!)		// snippet 경로에서 입력
	configurations(asciidoctorExt.name)				// extension으로 adoc -> html 변환
}

tasks.bootJar {
	dependsOn(tasks.asciidoctor)					// asciidoctor task 선행
	from(tasks.asciidoctor.get().outputDir) {		// 변환된 html 파일 있는 경로
		into("static/docs")					// resources로 복사
	}
}
