package com.stemguide.backend.dto

data class SubjectRequest(
    val name: String,
    val description: String
)

data class SubjectResponse(
    val id: Long,
    val name: String,
    val description: String
)
