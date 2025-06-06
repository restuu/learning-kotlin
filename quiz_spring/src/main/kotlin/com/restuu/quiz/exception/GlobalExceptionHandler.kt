package com.restuu.quiz.exception

import com.restuu.quiz.shared.dto.response.ApiResponse
import com.restuu.quiz.shared.dto.response.HTTPResponse
import com.restuu.quiz.shared.dto.response.toResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    suspend fun handleInternalServerError(e: Exception): HTTPResponse<Unit> {
        e.printStackTrace()

        return ApiResponse.internalServerError().toResponseEntity()
    }

}