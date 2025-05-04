package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getQuizQuestionById(quizQuestionRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions/{questionId}") {
        val questionId = call.parameters["questionId"]?.takeIf { it.isNotBlank() }
        if (questionId == null) {
            call.respond(
                message = "Question ID is missing",
                status = HttpStatusCode.BadRequest
            )
            return@get
        }

        quizQuestionRepository.getQuestionById(questionId)
            ?.let { call.respond(it) }
            ?: call.respond(message = "Question not found", status = HttpStatusCode.NotFound)
    }
}