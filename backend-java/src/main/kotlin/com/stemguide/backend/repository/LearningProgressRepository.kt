package com.stemguide.backend.repository

import com.stemguide.backend.domain.LearningProgress
import com.stemguide.backend.domain.UserProfile
import org.springframework.data.jpa.repository.JpaRepository

interface LearningProgressRepository : JpaRepository<LearningProgress, Long> {
    fun findByUser(user: UserProfile): List<LearningProgress>
}
