package com.stemguide.backend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "learning_progress")
data class LearningProgress(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: UserProfile? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    var resource: LearningResource? = null,

    @Column(nullable = false)
    var status: ProgressStatus = ProgressStatus.NOT_STARTED,

    @Column(nullable = false)
    var lastUpdated: LocalDateTime = LocalDateTime.now()
)

enum class ProgressStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED
}
