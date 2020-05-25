package com.songko.simpleshopserver.controller

import com.songko.simpleshopserver.common.ApiResponse
import com.songko.simpleshopserver.domain.auth.JWTUtil
import com.songko.simpleshopserver.domain.auth.SigninRequest
import com.songko.simpleshopserver.domain.auth.SigninService
import com.songko.simpleshopserver.domain.auth.UserContextHolder
import com.songko.simpleshopserver.interceptor.TokenValidationInterceptor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1")
class SigninApiController @Autowired constructor(
        private val signinService: SigninService,
        private val userContextHolder: UserContextHolder
) {
    @PostMapping("/signin")
    fun signin(@RequestBody signinRequest: SigninRequest) =
            ApiResponse.ok(signinService.signin(signinRequest))


    @PostMapping("/refresh_token")
    fun refreshToken(@RequestParam("grant_type") grantType: String): ApiResponse {
        if (grantType != TokenValidationInterceptor.GRANT_TYPE_REFRESH) {
            throw IllegalArgumentException("grant_type 없음")
        }

        return userContextHolder.email?.let {
            ApiResponse.ok(JWTUtil.createToken(it))
        } ?: throw IllegalArgumentException("사용자 정보 없음")
    }
}