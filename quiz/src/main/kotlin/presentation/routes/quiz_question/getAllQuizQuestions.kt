package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(quizQuestionRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions") {
        val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
        val topicCode = call.parameters["topicCode"]?.toIntOrNull()

        quizQuestionRepository.getAllQuizQuestions(topicCode, limit)
            .takeIf { it.isNotEmpty() }
            ?.let { call.respond(it) }
            ?: call.respond(message = "No questions found", status = HttpStatusCode.NotFound)
    }
}