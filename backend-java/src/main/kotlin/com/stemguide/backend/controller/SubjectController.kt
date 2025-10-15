package com.stemguide.backend.controller

import com.stemguide.backend.dto.SubjectRequest
import com.stemguide.backend.dto.SubjectResponse
import com.stemguide.backend.service.SubjectService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/subjects")
class SubjectController(
    private val subjectService: SubjectService
) {
    @GetMapping
    fun listSubjects(): List<SubjectResponse> = subjectService.getAllSubjects()
        .map { SubjectResponse(it.id, it.name, it.description) }

    @GetMapping("/{id}")
    fun getSubject(@PathVariable id: Long): SubjectResponse = subjectService.getSubject(id)
        .let { SubjectResponse(it.id, it.name, it.description) }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun createSubject(@RequestBody request: SubjectRequest): SubjectResponse = subjectService.createSubject(request)
        .let { SubjectResponse(it.id, it.name, it.description) }

    @PutMapping("/{id}")
    fun updateSubject(@PathVariable id: Long, @RequestBody request: SubjectRequest): SubjectResponse =
        subjectService.updateSubject(id, request)
            .let { SubjectResponse(it.id, it.name, it.description) }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteSubject(@PathVariable id: Long) = subjectService.deleteSubject(id)
}
