package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.UserInsertRequest
import org.shangahi.sellio_backend.api.dto.UserUpdateRequest
import org.shangahi.sellio_backend.api.dto.response.UserInfoResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.api.mapper.toUser
import org.shangahi.sellio_backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/user")
class UserInfoController(
    private val userService: UserService,
) {

    @PostMapping("/insert")
    fun insertUser(@RequestBody request: UserInsertRequest): ResponseEntity<UserInfoResponse> {
        val savedUser = userService.insertUser(request.toUser())
        return ResponseEntity.ok(savedUser.toResponse())
    }

    @PostMapping("/update")
    fun updateUser(@RequestBody request: UserUpdateRequest): ResponseEntity<UserInfoResponse> {
        val updatedUser = userService.updateUser(request.toUser())
        return ResponseEntity.ok(updatedUser.toResponse())
    }

    @GetMapping("/profile")
    fun getUserProfile(@RequestParam userId: UUID): ResponseEntity<UserInfoResponse> {
        val response = userService.findById(userId).toResponse()
        return ResponseEntity.ok(response)
    }
}
