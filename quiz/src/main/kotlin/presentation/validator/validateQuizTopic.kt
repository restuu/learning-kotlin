package com.restuu.presentation.validator

import com.restuu.domain.model.QuizTopic
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateQuizTopic() {
    validate<QuizTopic> { quizTopic ->
        when {
            quizTopic.name.isBlank() || quizTopic.name.length < 3 ->
                ValidationResult.Invalid("Topic name at least 3 characters")

            quizTopic.code < 0 ->
                ValidationResult.Invalid("Topic code must be >= 0")

            quizTopic.imageUrl.isBlank() ->
                ValidationResult.Invalid("Image URL must not be empty")

            else -> ValidationResult.Valid
        }
    }
}