package com.songko.simpleshopserver.common

import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice   // 전역적인 익셉션 핸들러임을 알려줌
@RestController
class CustomExceptionHandler {
    private val logger = LoggerFactory.getLogger(this::class.java)

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(e: CustomException): ApiResponse {
        logger.error("API error : " + e)
        return ApiResponse.error(e.message)
    }

    @ExceptionHandler(Exception::class)
    fun handleException(e: Exception): ApiResponse {
        logger.error("API error : " + e)
        return ApiResponse.error("알 수 없는 오류가 발생했습니다.")
    }
}