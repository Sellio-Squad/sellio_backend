package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.UserInsertRequest
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.UserDoc
import org.shangahi.sellio_backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User", description = "Endpoints for managing user operations")
class UserInfoController(
    private val userService: UserService,
) {
    @UserDoc.InsertUser
    @PostMapping("/insert")
    fun insertUser(@Valid @RequestBody request: UserInsertRequest): ResponseEntity<UserInfoResponse> {
        val response = userService.insertUser(request)
        val location = URI.create("/v1/users/${response.id}")
        return ResponseEntity
            .created(location)
            .body(response)
    }

    @UserDoc.UpdateUser
    @PostMapping("/{userId}/update")
    fun updateUser(
        @PathVariable userId: UUID,
        @Valid @RequestBody request: UserUpdateRequest
    ): ResponseEntity<UserInfoResponse> {
        val updatedUser = userService.updateUser(userId, request)
        return ResponseEntity.ok(updatedUser)
    }

    @UserDoc.GetUserInfo
    @GetMapping("/{userId}")
    fun getUserProfile(@PathVariable userId: UUID): ResponseEntity<UserInfoResponse> {
        val response = userService.findById(userId).toResponse()
        return ResponseEntity.ok(response)
    }
}
