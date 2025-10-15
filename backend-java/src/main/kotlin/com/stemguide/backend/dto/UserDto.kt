package com.stemguide.backend.dto

data class UserRequest(
    val email: String,
    val displayName: String,
    val interests: String
)

data class UserResponse(
    val id: Long,
    val email: String,
    val displayName: String,
    val interests: String
)
