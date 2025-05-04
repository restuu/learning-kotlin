package com.restuu.presentation.routes.quiz_question

import com.restuu.presentation.config.quizQuestions
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions() {
    get(path = "/quiz/questions") {
        val topicCode = call.queryParameters["topicCode"]?.toIntOrNull()
        val limit = call.queryParameters["limit"]?.toIntOrNull() ?: 1

        val filteredQuestions = quizQuestions
            .filter { it.topicCode == topicCode }
            .take(limit)

        call.respond(filteredQuestions)
    }
}