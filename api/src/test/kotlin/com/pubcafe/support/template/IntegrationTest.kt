package com.pubcafe.support.template

import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.web.filter.CharacterEncodingFilter

@SpringBootTest
//@Sql(scripts = ["classpath:db/setup.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(scripts = ["classpath:db/teardown.sql"], executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
abstract class IntegrationTest {

    // Mock Mvc
    protected lateinit var mockMvc: MockMvc

    @BeforeEach
    fun setUp(webApplicationContext: WebApplicationContext, restDocumentation: RestDocumentationContextProvider) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
            .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
            .apply<DefaultMockMvcBuilder>(documentationConfiguration(restDocumentation))
            .alwaysDo<DefaultMockMvcBuilder>(print())
            .build()
    }

    // Mock Server
    protected lateinit var mockWebServer: MockWebServer

    @Value("\${out.mock-webserver.port}")
    private var mockWebServerPort: Int = 0

    @BeforeAll
    fun startServer() {
        mockWebServer = MockWebServer()
        mockWebServer.start(port = mockWebServerPort)
    }

    @AfterAll
    fun stopServer() {
        mockWebServer.shutdown()
    }

    // json converter
    @Autowired
    protected lateinit var objectMapper: ObjectMapper

    /**
     * 유틸 메서드
     */
    protected fun toJson(any: Any): String {
        return objectMapper.writeValueAsString(any)
    }
}