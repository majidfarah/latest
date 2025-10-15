package com.stemguide.backend.dto

import com.stemguide.backend.domain.ProgressStatus
import java.time.LocalDateTime

data class ProgressRequest(
    val userId: Long,
    val resourceId: Long,
    val status: ProgressStatus
)

data class ProgressResponse(
    val id: Long,
    val userId: Long,
    val resourceId: Long,
    val status: ProgressStatus,
    val lastUpdated: LocalDateTime
)
