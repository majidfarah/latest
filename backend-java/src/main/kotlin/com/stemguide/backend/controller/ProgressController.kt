package com.stemguide.backend.controller

import com.stemguide.backend.domain.ProgressStatus
import com.stemguide.backend.dto.ProgressRequest
import com.stemguide.backend.dto.ProgressResponse
import com.stemguide.backend.service.ProgressService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/progress")
class ProgressController(
    private val progressService: ProgressService
) {
    @GetMapping("/user/{userId}")
    fun getProgressForUser(@PathVariable userId: Long): List<ProgressResponse> =
        progressService.getProgressForUser(userId).map {
            ProgressResponse(
                id = it.id,
                userId = it.user?.id ?: 0,
                resourceId = it.resource?.id ?: 0,
                status = it.status,
                lastUpdated = it.lastUpdated
            )
        }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createProgress(@RequestBody request: ProgressRequest): ProgressResponse =
        progressService.createProgress(request).let {
            ProgressResponse(
                id = it.id,
                userId = it.user?.id ?: 0,
                resourceId = it.resource?.id ?: 0,
                status = it.status,
                lastUpdated = it.lastUpdated
            )
        }

    @PutMapping("/{id}")
    fun updateProgress(@PathVariable id: Long, @RequestParam status: ProgressStatus): ProgressResponse =
        progressService.updateProgress(id, status).let {
            ProgressResponse(
                id = it.id,
                userId = it.user?.id ?: 0,
                resourceId = it.resource?.id ?: 0,
                status = it.status,
                lastUpdated = it.lastUpdated
            )
        }
}
