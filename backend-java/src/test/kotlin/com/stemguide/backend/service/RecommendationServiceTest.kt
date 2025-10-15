package com.stemguide.backend.service

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.stemguide.backend.dto.RecommendationGoal
import com.stemguide.backend.dto.RecommendationRequest
import com.stemguide.backend.dto.RecommendationResource
import com.stemguide.backend.dto.RecommendationResponse
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient

class RecommendationServiceTest {
    private val objectMapper = jacksonObjectMapper()
    private lateinit var server: MockWebServer
    private lateinit var service: RecommendationService

    @BeforeEach
    fun setUp() {
        server = MockWebServer().apply { start() }
        service = RecommendationService(server.url("/").toString().removeSuffix("/"), WebClient.builder())
    }

    @AfterEach
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun `delegates recommendation request to ai service`() {
        val payload = RecommendationResponse(
            subject = "Mathematics",
            level = "BEGINNER",
            overview = "Overview",
            recommendedResources = listOf(
                RecommendationResource(
                    title = "Algebra",
                    url = "https://example.com",
                    provider = "Khan Academy",
                    description = "Basics",
                )
            ),
            milestones = listOf("Start here"),
        )
        server.enqueue(
            MockResponse()
                .setResponseCode(200)
                .addHeader("Content-Type", "application/json")
                .setBody(objectMapper.writeValueAsString(payload))
        )

        val request = RecommendationRequest(
            subject = "Mathematics",
            level = "BEGINNER",
            goals = listOf(RecommendationGoal(topic = "algebra", priority = 1))
        )

        val result = service.getLearningPath(request)

        val recorded = server.takeRequest()
        assertEquals("/api/v1/learning-path", recorded.path)
        assertEquals("POST", recorded.method)
        assertEquals(payload.subject, result.subject)
        assertEquals(payload.recommendedResources.size, result.recommendedResources.size)
    }

    @Test
    fun `propagates ai service failures with descriptive message`() {
        server.enqueue(MockResponse().setResponseCode(500).setBody("internal error"))

        val exception = assertThrows(IllegalStateException::class.java) {
            service.getLearningPath(
                RecommendationRequest(
                    subject = "Physics",
                    level = "BEGINNER",
                    goals = emptyList(),
                )
            )
        }

        assertEquals("Failed to retrieve learning path from AI service: internal error", exception.message)
    }
}
