package com.stemguide.backend.repository

import com.stemguide.backend.domain.LearningResource
import com.stemguide.backend.domain.ResourceDifficulty
import com.stemguide.backend.domain.Subject
import org.springframework.data.jpa.repository.JpaRepository

interface LearningResourceRepository : JpaRepository<LearningResource, Long> {
    fun findBySubject(subject: Subject): List<LearningResource>
    fun findBySubjectAndDifficulty(subject: Subject, difficulty: ResourceDifficulty): List<LearningResource>
}
