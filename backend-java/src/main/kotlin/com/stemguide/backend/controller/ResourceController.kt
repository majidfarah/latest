package com.stemguide.backend.controller

import com.stemguide.backend.domain.ResourceDifficulty
import com.stemguide.backend.dto.ResourceRequest
import com.stemguide.backend.dto.ResourceResponse
import com.stemguide.backend.service.ResourceService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
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
@RequestMapping("/api/v1/resources")
class ResourceController(
    private val resourceService: ResourceService
) {
    @GetMapping
    fun getResources(): List<ResourceResponse> = resourceService.getResources().map {
        ResourceResponse(
            id = it.id,
            title = it.title,
            url = it.url,
            provider = it.provider,
            difficulty = it.difficulty,
            description = it.description,
            subjectId = it.subject?.id ?: 0,
            subjectName = it.subject?.name ?: ""
        )
    }

    @GetMapping("/subject/{subjectId}")
    fun getResourcesForSubject(
        @PathVariable subjectId: Long,
        @RequestParam(required = false) difficulty: ResourceDifficulty?
    ): List<ResourceResponse> = resourceService.getResourcesForSubject(subjectId, difficulty).map {
        ResourceResponse(
            id = it.id,
            title = it.title,
            url = it.url,
            provider = it.provider,
            difficulty = it.difficulty,
            description = it.description,
            subjectId = it.subject?.id ?: 0,
            subjectName = it.subject?.name ?: ""
        )
    }

    @GetMapping("/{id}")
    fun getResource(@PathVariable id: Long): ResourceResponse = resourceService.getResource(id).let {
        ResourceResponse(
            id = it.id,
            title = it.title,
            url = it.url,
            provider = it.provider,
            difficulty = it.difficulty,
            description = it.description,
            subjectId = it.subject?.id ?: 0,
            subjectName = it.subject?.name ?: ""
        )
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createResource(@RequestBody request: ResourceRequest): ResourceResponse = resourceService.createResource(request).let {
        ResourceResponse(
            id = it.id,
            title = it.title,
            url = it.url,
            provider = it.provider,
            difficulty = it.difficulty,
            description = it.description,
            subjectId = it.subject?.id ?: 0,
            subjectName = it.subject?.name ?: ""
        )
    }

    @PutMapping("/{id}")
    fun updateResource(@PathVariable id: Long, @RequestBody request: ResourceRequest): ResourceResponse =
        resourceService.updateResource(id, request).let {
            ResourceResponse(
                id = it.id,
                title = it.title,
                url = it.url,
                provider = it.provider,
                difficulty = it.difficulty,
                description = it.description,
                subjectId = it.subject?.id ?: 0,
                subjectName = it.subject?.name ?: ""
            )
        }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteResource(@PathVariable id: Long) = resourceService.deleteResource(id)
}
