package com.stemguide.backend.service

import com.stemguide.backend.domain.LearningResource
import com.stemguide.backend.domain.ResourceDifficulty
import com.stemguide.backend.dto.ResourceRequest
import com.stemguide.backend.repository.LearningResourceRepository
import com.stemguide.backend.repository.SubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ResourceService(
    private val learningResourceRepository: LearningResourceRepository,
    private val subjectRepository: SubjectRepository
) {
    fun getResources(): List<LearningResource> = learningResourceRepository.findAll()

    fun getResourcesForSubject(subjectId: Long, difficulty: ResourceDifficulty?): List<LearningResource> {
        val subject = subjectRepository.findById(subjectId)
            .orElseThrow { IllegalArgumentException("Subject with id $subjectId not found") }
        return difficulty?.let { learningResourceRepository.findBySubjectAndDifficulty(subject, it) }
            ?: learningResourceRepository.findBySubject(subject)
    }

    fun getResource(id: Long): LearningResource = learningResourceRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Resource with id $id not found") }

    fun createResource(request: ResourceRequest): LearningResource {
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { IllegalArgumentException("Subject with id ${request.subjectId} not found") }
        return learningResourceRepository.save(
            LearningResource(
                title = request.title,
                url = request.url,
                provider = request.provider,
                difficulty = request.difficulty,
                description = request.description,
                subject = subject
            )
        )
    }

    @Transactional
    fun updateResource(id: Long, request: ResourceRequest): LearningResource {
        val resource = getResource(id)
        val subject = subjectRepository.findById(request.subjectId)
            .orElseThrow { IllegalArgumentException("Subject with id ${request.subjectId} not found") }
        resource.title = request.title
        resource.url = request.url
        resource.provider = request.provider
        resource.difficulty = request.difficulty
        resource.description = request.description
        resource.subject = subject
        return resource
    }

    fun deleteResource(id: Long) {
        if (!learningResourceRepository.existsById(id)) {
            throw IllegalArgumentException("Resource with id $id not found")
        }
        learningResourceRepository.deleteById(id)
    }
}
