package com.stemguide.backend.service

import com.stemguide.backend.domain.LearningProgress
import com.stemguide.backend.domain.ProgressStatus
import com.stemguide.backend.dto.ProgressRequest
import com.stemguide.backend.repository.LearningProgressRepository
import com.stemguide.backend.repository.LearningResourceRepository
import com.stemguide.backend.repository.UserProfileRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class ProgressService(
    private val learningProgressRepository: LearningProgressRepository,
    private val userProfileRepository: UserProfileRepository,
    private val learningResourceRepository: LearningResourceRepository
) {
    fun getProgressForUser(userId: Long): List<LearningProgress> {
        val user = userProfileRepository.findById(userId)
            .orElseThrow { IllegalArgumentException("User with id $userId not found") }
        return learningProgressRepository.findByUser(user)
    }

    fun createProgress(request: ProgressRequest): LearningProgress {
        val user = userProfileRepository.findById(request.userId)
            .orElseThrow { IllegalArgumentException("User with id ${request.userId} not found") }
        val resource = learningResourceRepository.findById(request.resourceId)
            .orElseThrow { IllegalArgumentException("Resource with id ${request.resourceId} not found") }
        return learningProgressRepository.save(
            LearningProgress(
                user = user,
                resource = resource,
                status = request.status,
                lastUpdated = LocalDateTime.now()
            )
        )
    }

    @Transactional
    fun updateProgress(id: Long, status: ProgressStatus): LearningProgress {
        val progress = learningProgressRepository.findById(id)
            .orElseThrow { IllegalArgumentException("Progress with id $id not found") }
        progress.status = status
        progress.lastUpdated = LocalDateTime.now()
        return progress
    }
}
