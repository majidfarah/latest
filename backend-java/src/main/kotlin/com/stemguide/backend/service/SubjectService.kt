package com.stemguide.backend.service

import com.stemguide.backend.domain.Subject
import com.stemguide.backend.dto.SubjectRequest
import com.stemguide.backend.repository.SubjectRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SubjectService(
    private val subjectRepository: SubjectRepository
) {
    fun getAllSubjects(): List<Subject> = subjectRepository.findAll().sortedBy { it.name }

    fun getSubject(id: Long): Subject = subjectRepository.findById(id)
        .orElseThrow { IllegalArgumentException("Subject with id $id not found") }

    fun createSubject(request: SubjectRequest): Subject {
        if (subjectRepository.findByNameIgnoreCase(request.name).isPresent) {
            throw IllegalArgumentException("Subject ${request.name} already exists")
        }
        return subjectRepository.save(
            Subject(
                name = request.name,
                description = request.description
            )
        )
    }

    @Transactional
    fun updateSubject(id: Long, request: SubjectRequest): Subject {
        val subject = getSubject(id)
        subject.name = request.name
        subject.description = request.description
        return subject
    }

    fun deleteSubject(id: Long) {
        if (!subjectRepository.existsById(id)) {
            throw IllegalArgumentException("Subject with id $id not found")
        }
        subjectRepository.deleteById(id)
    }
}
