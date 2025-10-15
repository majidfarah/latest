package com.stemguide.backend.service

import com.stemguide.backend.dto.RecommendationRequest
import com.stemguide.backend.dto.RecommendationResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class RecommendationService(
    @Value("\${services.ai.base-url:http://localhost:8000}")
    private val baseUrl: String,
    builder: WebClient.Builder
) {
    private val client: WebClient = builder.baseUrl(baseUrl).build()

    fun getLearningPath(request: RecommendationRequest): RecommendationResponse =
        client.post()
            .uri("/api/v1/learning-path")
            .bodyValue(request)
            .retrieve()
            .onStatus(HttpStatusCode::isError) { response ->
                response.bodyToMono(String::class.java)
                    .defaultIfEmpty("AI service returned ${'$'}{response.statusCode()}")
                    .map { message ->
                        IllegalStateException(
                            "Failed to retrieve learning path from AI service: ${'$'}message"
                        )
                    }
            }
            .bodyToMono(RecommendationResponse::class.java)
            .switchIfEmpty(
                Mono.error(IllegalStateException("AI service returned an empty response"))
            )
            .block()
}
