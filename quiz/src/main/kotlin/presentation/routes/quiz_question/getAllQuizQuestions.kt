package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Route

fun Route.getAllQuizQuestions(quizQuestionRepository: QuizQuestionRepository) {
    get<QuizQuestionRoutesPath> { path ->
        val limit = path.limit
        val topicCode = path.topicCode

        quizQuestionRepository.getAllQuizQuestions(topicCode, limit)
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }

    }
}