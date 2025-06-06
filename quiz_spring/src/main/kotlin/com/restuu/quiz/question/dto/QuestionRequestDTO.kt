package com.restuu.quiz.question.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

@JsonSerialize
data class QuestionRequestDTO(
    val id: String? = null,

    @field:NotBlank(message = "Question must not be empty")
    @field:Size(min = 5, message = "Question must be at least 5 characters long")
    val question: String,

    @field:NotBlank(message = "Correct answer must not be empty")
    val correctAnswer: String,

    @field:NotEmpty(message = "At least one incorrect answer must be provided")
    val incorrectAnswers: List<@NotBlank(message = "Incorrect answer must not be empty") String>,

    @field:NotBlank(message = "Explanation must not be empty")
    val explanation: String,

    @field:Min(value = 1, message = "Topic code must be greater than 0")
    val topicCode: Int,
)
