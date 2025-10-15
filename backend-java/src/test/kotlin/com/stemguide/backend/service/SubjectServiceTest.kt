package com.stemguide.backend.service

import com.stemguide.backend.dto.SubjectRequest
import com.stemguide.backend.repository.SubjectRepository
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class SubjectServiceTest @Autowired constructor(
    private val subjectRepository: SubjectRepository,
) {
    private lateinit var subjectService: SubjectService

    @BeforeEach
    fun setUp() {
        subjectService = SubjectService(subjectRepository)
    }

    @Test
    fun `createSubject persists a unique subject`() {
        val result = subjectService.createSubject(
            SubjectRequest(
                name = "Mathematics",
                description = "Core mathematical ideas",
            )
        )

        assertEquals("Mathematics", result.name)
        assertEquals(1, subjectRepository.count())
    }

    @Test
    fun `createSubject rejects duplicates ignoring case`() {
        subjectService.createSubject(
            SubjectRequest(
                name = "Physics",
                description = "Mechanics and more",
            )
        )

        val exception = assertThrows(IllegalArgumentException::class.java) {
            subjectService.createSubject(
                SubjectRequest(
                    name = "physics",
                    description = "Duplicate name should not be allowed",
                )
            )
        }

        assertEquals("Subject physics already exists", exception.message)
    }
}
