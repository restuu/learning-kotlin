package com.restuu.presentation.routes.quiz_question

import com.restuu.presentation.config.quizQuestions
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete

fun Route.deleteQuizQuestionById() {
    delete(path = "/quiz/questions/{questionId}") {
        val id = call.parameters["questionId"]

        quizQuestions.removeIf { it.id == id }
        call.respondText("Question removed")
    }
}