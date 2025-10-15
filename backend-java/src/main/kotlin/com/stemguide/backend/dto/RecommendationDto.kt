package com.stemguide.backend.dto

import com.fasterxml.jackson.annotation.JsonAlias

data class RecommendationGoal(
    val topic: String,
    val priority: Int? = null
)

data class RecommendationRequest(
    val subject: String,
    val level: String,
    val goals: List<RecommendationGoal> = emptyList()
)

data class RecommendationResponse(
    val subject: String,
    val level: String,
    val overview: String,
    @JsonAlias("recommended_resources")
    val recommendedResources: List<RecommendationResource>,
    val milestones: List<String>
)

data class RecommendationResource(
    val title: String,
    val url: String,
    val provider: String,
    val description: String
)
