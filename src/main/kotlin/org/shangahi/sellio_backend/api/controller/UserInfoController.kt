package org.shangahi.sellio_backend.api.controller

import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.shangahi.sellio_backend.api.dto.request.UserUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.swagger.doc.UserDoc
import org.shangahi.sellio_backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.util.*

@RestController
@RequestMapping("/v1/user")
@Tag(name = "User", description = "Endpoints for managing user operations")
class UserInfoController(
    private val userService: UserService,
) {

    @UserDoc.UpdateUser
    @PatchMapping("/update")
    fun updateUser(
        @AuthenticationPrincipal userId: UUID,
        @Valid @RequestBody request: UserUpdateRequest,
    ): ResponseEntity<UserInfoResponse> {
        val updatedUser = userService.updateUser(userId, request)
        return ResponseEntity.ok(updatedUser.toResponse())
    }

    @UserDoc.GetUserProfile
    @GetMapping("/profile")
    fun getUserProfile(@AuthenticationPrincipal userId: UUID): ResponseEntity<UserInfoResponse> {
        val response = userService.findById(userId).toResponse()
        return ResponseEntity.ok(response)
    }

    @PostMapping("/avatar")
    fun uploadUserAvatar(
        @AuthenticationPrincipal userId: UUID,
        @RequestPart("image") file: MultipartFile
    ): ResponseEntity<UserInfoResponse> {
        val updatedUser = userService.uploadUserAvatar(userId, file)
        return ResponseEntity.ok(updatedUser.toResponse())
    }

    @DeleteMapping("/delete")
    fun deleteUser(@AuthenticationPrincipal userId: UUID) {
        userService.deleteUser(userId)
    }

}