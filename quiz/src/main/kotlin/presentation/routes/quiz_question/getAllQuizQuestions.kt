package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(quizQuestionRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions") {
        val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
        val topicCode = call.parameters["topicCode"]?.toIntOrNull()

        quizQuestionRepository.getAllQuizQuestions(topicCode, limit)
            .onSuccess { call::respond }
            .onFailure { ::respondWithError }

    }
}