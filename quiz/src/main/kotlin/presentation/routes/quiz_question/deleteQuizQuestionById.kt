package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.DataError
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
) {
    delete(path = "/quiz/questions/{questionId}") {
        val questionId: String = call.parameters["questionId"]
            ?: return@delete call.respond(
                message = "Question ID is required",
                status = HttpStatusCode.BadRequest
            )

        quizQuestionRepository.deleteQuestionById(questionId)
            .onSuccess { call.respondText("Question removed") }
            .onFailure { error ->
                when (error) {
                    DataError.NotFound ->
                        call.respond(message = "Question not found", status = HttpStatusCode.NotFound)

                    else ->
                        call.respond(message = "Error deleting question", status = HttpStatusCode.InternalServerError)
                }
            }
    }
}