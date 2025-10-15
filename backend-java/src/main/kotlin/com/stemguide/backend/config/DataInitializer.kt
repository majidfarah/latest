package com.stemguide.backend.config

import com.stemguide.backend.domain.ResourceDifficulty
import com.stemguide.backend.dto.ResourceRequest
import com.stemguide.backend.dto.SubjectRequest
import com.stemguide.backend.repository.SubjectRepository
import com.stemguide.backend.service.ResourceService
import com.stemguide.backend.service.SubjectService
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!test")
class DataInitializer(
    private val subjectService: SubjectService,
    private val resourceService: ResourceService,
    private val subjectRepository: SubjectRepository
) {
    private val logger = LoggerFactory.getLogger(DataInitializer::class.java)

    @PostConstruct
    fun seedData() {
        if (subjectRepository.count() > 0) {
            logger.info("Skipping data initialization, subjects already exist")
            return
        }

        logger.info("Seeding STEM subjects and baseline resources")

        val subjects = listOf(
            SubjectRequest("Mathematics", "Core mathematical foundations covering algebra, calculus, and discrete math."),
            SubjectRequest("Physics", "Classical mechanics, electromagnetism, and introductory quantum concepts."),
            SubjectRequest("Software Engineering", "Software design principles, coding best practices, and tooling."),
            SubjectRequest("Artificial Intelligence", "Machine learning fundamentals, neural networks, and applied AI."),
            SubjectRequest("Engineering", "Systems thinking, design, and cross-disciplinary engineering skills.")
        ).map(subjectService::createSubject)

        val math = subjects.first { it.name == "Mathematics" }
        val physics = subjects.first { it.name == "Physics" }
        val software = subjects.first { it.name == "Software Engineering" }
        val ai = subjects.first { it.name == "Artificial Intelligence" }

        resourceService.createResource(
            ResourceRequest(
                title = "Khan Academy Calculus 1",
                url = "https://www.khanacademy.org/math/calculus-1",
                provider = "Khan Academy",
                difficulty = ResourceDifficulty.BEGINNER,
                description = "Interactive calculus lessons with practice problems and quizzes.",
                subjectId = math.id
            )
        )

        resourceService.createResource(
            ResourceRequest(
                title = "MIT OCW Classical Mechanics",
                url = "https://ocw.mit.edu/courses/8-01sc-classical-mechanics-fall-2016/",
                provider = "MIT OpenCourseWare",
                difficulty = ResourceDifficulty.INTERMEDIATE,
                description = "Video lectures, notes, and assignments covering Newtonian mechanics.",
                subjectId = physics.id
            )
        )

        resourceService.createResource(
            ResourceRequest(
                title = "Harvard CS50",
                url = "https://cs50.harvard.edu/x/2023/",
                provider = "Harvard University",
                difficulty = ResourceDifficulty.BEGINNER,
                description = "Comprehensive introduction to computer science and programming.",
                subjectId = software.id
            )
        )

        resourceService.createResource(
            ResourceRequest(
                title = "Fast.ai Practical Deep Learning",
                url = "https://course.fast.ai/",
                provider = "Fast.ai",
                difficulty = ResourceDifficulty.INTERMEDIATE,
                description = "Hands-on deep learning course with notebooks and applied projects.",
                subjectId = ai.id
            )
        )
    }
}
