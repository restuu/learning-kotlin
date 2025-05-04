package com.restuu.presentation.routes.quiz_question

import com.restuu.presentation.config.quizQuestions
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getQuizQuestionById() {
    get(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"]

        val question = quizQuestions.find { it.id == id }

        if (question == null) {
            call.respondText("Question not found")
            return@get
        }
        call.respond(question)
    }
}