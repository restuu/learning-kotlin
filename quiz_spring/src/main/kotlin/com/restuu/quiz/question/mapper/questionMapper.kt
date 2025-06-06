package com.restuu.quiz.question.mapper

import com.restuu.quiz.question.Question
import com.restuu.quiz.question.dto.QuestionRequestDTO
import com.restuu.quiz.question.dto.QuestionResponseDTO
import org.bson.types.ObjectId

fun Question.toQuestionResponseDTO() = QuestionResponseDTO(
    id = id?.toHexString(),
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode
)

fun QuestionRequestDTO.toQuestion() = Question(
    id = id?.let { ObjectId(it) },
    question = question,
    correctAnswer = correctAnswer,
    incorrectAnswers = incorrectAnswers,
    explanation = explanation,
    topicCode = topicCode
)