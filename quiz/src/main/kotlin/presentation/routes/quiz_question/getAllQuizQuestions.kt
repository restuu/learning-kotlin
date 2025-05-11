package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.DataError
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions(quizQuestionRepository: QuizQuestionRepository) {
    get(path = "/quiz/questions") {
        val limit = call.parameters["limit"]?.toIntOrNull() ?: 10
        val topicCode = call.parameters["topicCode"]?.toIntOrNull()

        quizQuestionRepository.getAllQuizQuestions(topicCode, limit)
            .onSuccess { questions ->
                call.respond(questions)
            }
            .onFailure { error ->
                if (error == DataError.NotFound) {
                    return@get call.respond(
                        message = "No questions found",
                        status = HttpStatusCode.NotFound,
                    )
                }

                call.respond(
                    message = "Unknown error",
                    status = HttpStatusCode.InternalServerError,
                )
            }

    }
}