package com.restuu.quiz.shared.dto.response

import com.fasterxml.jackson.annotation.JsonInclude
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import kotlin.math.ceil


sealed class ApiResponse<out T: Any> {
    abstract val statusCode: Int
    abstract val message: String

    data class Success<out T: Any>(
        override val statusCode: Int,
        override val message: String,
        val data: T? = null,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val pagination: Pagination? = null,
    ): ApiResponse<T>()

    data class Failed(
        override val statusCode: Int,
        override val message: String,
        @JsonInclude(JsonInclude.Include.NON_NULL)
        val errors: List<String>? = null,
    ): ApiResponse<Unit>()

    data class Pagination(
        val page: Int,
        val size: Int,
        val totalData: Long,
    ) {
        val totalPages: Int
            get() = if (size > 0) {
                ceil(totalData.toDouble() / size).toInt()
            } else 0
    }

    companion object {
        fun <T: Any> success(data: T? = null) = Success(200, "OK", data)
        fun badRequest(errors: List<String>? = null) = Failed(400, "Bad Request", errors)
        fun internalServerError() = Failed(500, "Internal Server Error")
    }
}

fun <T: Any> ApiResponse<T>.toResponseEntity() =
    ResponseEntity(this, HttpStatus.valueOf(statusCode))

typealias HTTPResponse<T> = ResponseEntity<ApiResponse<T>>