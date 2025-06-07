package com.restuu.quiz.exception

import com.restuu.quiz.shared.dto.response.ApiResponse
import com.restuu.quiz.shared.dto.response.HTTPResponse
import com.restuu.quiz.shared.dto.response.toResponseEntity
import org.springframework.core.codec.DecodingException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import org.springframework.web.server.ServerWebInputException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(Exception::class)
    suspend fun handleInternalServerError(e: Exception): HTTPResponse<Unit> {
        e.printStackTrace()

        return ApiResponse.internalServerError().toResponseEntity()
    }

    @ExceptionHandler(WebExchangeBindException::class)
    suspend fun handleWebExchangeBindException(e: WebExchangeBindException): HTTPResponse<Unit> {
        val errors = mutableListOf<String>()

        e.bindingResult.fieldErrors.forEach {
            errors.add("${it.field}: ${it.defaultMessage}")
        }

        return ApiResponse.badRequest(errors).toResponseEntity()
    }

    @ExceptionHandler(ServerWebInputException::class)
    suspend fun handleServerWebInputException(e: ServerWebInputException): HTTPResponse<Unit> {
        val errors = mutableListOf<String>()

        when (val cause = e.cause) {
            is DecodingException -> {
                cause.localizedMessage.let { errors.add(it) }
            }

            else -> {
                errors.add(cause?.message ?: "invalid request payload")
            }
        }

        return ApiResponse.badRequest(errors).toResponseEntity()
    }
    
}