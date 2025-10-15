package com.stemguide.backend.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "learning_resources")
data class LearningResource(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var title: String = "",

    @Column(nullable = false)
    var url: String = "",

    @Column(nullable = false)
    var provider: String = "",

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var difficulty: ResourceDifficulty = ResourceDifficulty.BEGINNER,

    @Column(length = 4000)
    var description: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    var subject: Subject? = null
)

enum class ResourceDifficulty {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}
