package com.restuu.presentation.validator

import com.restuu.domain.model.QuizQuestion
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateQuizQuestion() {
    validate<QuizQuestion> { question ->
        when {
            question.question.isBlank() || question.question.length < 5 ->
                ValidationResult.Invalid("Question must be at least 5 characters long")
            question.correctAnswer.isBlank() ->
                ValidationResult.Invalid("Correct answer cannot be empty")
            question.incorrectAnswers.isEmpty() ->
                ValidationResult.Invalid("There must be at least 1 incorrect answers")
            question.incorrectAnswers.any { it.isBlank() } ->
                ValidationResult.Invalid("Incorrect answer cannot be blank")
            question.explanation.isBlank() ->
                ValidationResult.Invalid("Explanation cannot be empty")
            question.topicCode <= 0 ->
                ValidationResult.Invalid("Topic code must be greater than zero")
            else -> ValidationResult.Valid
        }
    }
}