package com.stemguide.backend.service

import com.stemguide.backend.domain.UserProfile
import com.stemguide.backend.repository.UserProfileRepository
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userProfileRepository: UserProfileRepository
) {
    fun listUsers(): List<UserProfile> = userProfileRepository.findAll()

    fun getUser(id: Long): UserProfile = userProfileRepository.findById(id)
        .orElseThrow { IllegalArgumentException("User with id $id not found") }

    fun createUser(email: String, displayName: String, interests: String): UserProfile {
        if (userProfileRepository.findByEmail(email).isPresent) {
            throw IllegalArgumentException("User with email $email already exists")
        }
        return userProfileRepository.save(
            UserProfile(
                email = email,
                displayName = displayName,
                interests = interests
            )
        )
    }
}
