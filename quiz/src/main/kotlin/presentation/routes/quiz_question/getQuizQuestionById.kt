package com.restuu.presentation.routes.quiz_question

import com.restuu.presentation.config.quizQuestions
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getQuizQuestionById() {
    get(path = "/quiz/questions/{questionId}") {
        call.parameters["questionId"]
            ?.takeIf { it.isNotBlank() }
            ?.let { id ->
                quizQuestions.find { it.id == id }
                    .takeIf { it != null }
                    ?.let { call.respond(it) }
                    ?: call.respond(
                        message = "Question not found",
                        status = HttpStatusCode.NotFound
                    )
            }
            ?: call.respond(
                message = "Question ID is missing",
                status = HttpStatusCode.BadRequest
            )
    }
}