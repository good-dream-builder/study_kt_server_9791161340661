package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.domain.auth.SignupRequest
import com.songko.simpleshopserver.domain.auth.SignupService
import com.songko.simpleshopserver.domain.auth.UserContextHolder
import com.songko.simpleshopserver.domain.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class UserApiController @Autowired constructor(
        private val signupService: SignupService,
        private val userService: UserService,
        private val userContextHolder: UserContextHolder
) {
    @PostMapping("/users")
    fun signup(@RequestBody signupRequest: SignupRequest) =
            ApiResponse.ok(signupService.signup(signupRequest))

    @PutMapping("/users/fcm-token")
    fun updateFcmToken(@RequestBody fcmToken: String) =
            userContextHolder.id?.let { userId ->
                ApiResponse.ok(userService.updateFcmToke(userId, fcmToken))
            } ?: ApiResponse.error("토큰 갱신 실패")
}