package com.stemguide.backend.controller

import com.stemguide.backend.dto.UserRequest
import com.stemguide.backend.dto.UserResponse
import com.stemguide.backend.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun listUsers(): List<UserResponse> = userService.listUsers().map {
        UserResponse(it.id, it.email, it.displayName, it.interests)
    }

    @GetMapping("/{id}")
    fun getUser(@PathVariable id: Long): UserResponse = userService.getUser(id).let {
        UserResponse(it.id, it.email, it.displayName, it.interests)
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createUser(@RequestBody request: UserRequest): UserResponse = userService.createUser(
        email = request.email,
        displayName = request.displayName,
        interests = request.interests
    ).let {
        UserResponse(it.id, it.email, it.displayName, it.interests)
    }
}
