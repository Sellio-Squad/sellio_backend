package org.shangahi.sellio_backend.api.controller

import org.shangahi.sellio_backend.api.dto.UserInfoResponse
import org.shangahi.sellio_backend.api.mapper.toResponse
import org.shangahi.sellio_backend.service.UserService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/v1/user")
class UserInfoController(
    private val userService: UserService,
    @param:Value("spring.datasource.url") private val cdnEndpoint: String,
    ) {

    @GetMapping("/profile")
    fun getUserProfile(@RequestParam userId: UUID): ResponseEntity<UserInfoResponse> {
        val response = userService.findById(userId).toResponse(cdnEndpoint)
        return ResponseEntity.ok(response)
    }
}