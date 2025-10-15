package com.stemguide.backend.repository

import com.stemguide.backend.domain.UserProfile
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface UserProfileRepository : JpaRepository<UserProfile, Long> {
    fun findByEmail(email: String): Optional<UserProfile>
}
