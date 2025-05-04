package com.restuu.presentation.routes.quiz_question

import com.restuu.presentation.config.quizQuestions
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions() {
    get(path = "/quiz/questions") {
        call.parameters["topicCode"]?.toIntOrNull()
            .let { topicCode ->
                quizQuestions.filter { topicCode == null || it.topicCode == topicCode }
            }
            .take(call.parameters["limit"]?.toIntOrNull() ?: 10)
            .takeIf { it.isNotEmpty() }
            ?.let { call.respond(it) }
            ?: call.respond(message = "No questions found", status = HttpStatusCode.NotFound)
    }
}