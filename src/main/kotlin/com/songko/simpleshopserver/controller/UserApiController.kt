package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.domain.auth.SignupRequest
import com.songko.simpleshopserver.domain.auth.SignupService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
        private val signupService: SignupService
){
    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest) =
            ApiResponse.ok(signupService.signup(signupRequest))
}