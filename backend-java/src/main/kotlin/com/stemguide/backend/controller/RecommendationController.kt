package com.stemguide.backend.controller

import com.stemguide.backend.dto.RecommendationRequest
import com.stemguide.backend.dto.RecommendationResponse
import com.stemguide.backend.service.RecommendationService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/recommendations")
class RecommendationController(
    private val recommendationService: RecommendationService
) {
    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    fun generateRecommendations(@RequestBody request: RecommendationRequest): RecommendationResponse =
        recommendationService.getLearningPath(request)
}
