package com.stemguide.backend.dto

import com.stemguide.backend.domain.ResourceDifficulty

data class ResourceRequest(
    val title: String,
    val url: String,
    val provider: String,
    val difficulty: ResourceDifficulty,
    val description: String,
    val subjectId: Long
)

data class ResourceResponse(
    val id: Long,
    val title: String,
    val url: String,
    val provider: String,
    val difficulty: ResourceDifficulty,
    val description: String,
    val subjectId: Long,
    val subjectName: String
)
