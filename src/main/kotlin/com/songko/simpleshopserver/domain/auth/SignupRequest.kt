package com.songko.simpleshopserver.domain.auth

data class SignupRequest(
        val email: String,
        val name: String,
        val password: String
)