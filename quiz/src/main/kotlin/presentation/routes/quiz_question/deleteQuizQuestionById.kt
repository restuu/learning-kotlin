package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
) {
    delete(path = "/quiz/questions/{questionId}") {
        call.parameters["questionId"]
            ?.takeIf { it.isNotBlank() }
            ?.let { quizQuestionRepository.deleteQuestionById(it) }
            ?.let { deleted ->
                when (deleted) {
                    true -> call.respondText("Question removed")
                    false -> call.respond(message = "Question not found", status = HttpStatusCode.NoContent)
                }
            }
            ?: call.respond(message = "Question ID is required", status = HttpStatusCode.BadRequest)
    }
}