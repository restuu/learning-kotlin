package com.restuu.quiz.question.dto

import com.fasterxml.jackson.databind.annotation.JsonSerialize

@JsonSerialize
data class QuestionResponseDTO(
    val id: String? = null,
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val explanation: String,
    val topicCode: Int,
)