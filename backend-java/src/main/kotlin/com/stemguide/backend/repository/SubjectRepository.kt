package com.stemguide.backend.repository

import com.stemguide.backend.domain.Subject
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface SubjectRepository : JpaRepository<Subject, Long> {
    fun findByNameIgnoreCase(name: String): Optional<Subject>
}
