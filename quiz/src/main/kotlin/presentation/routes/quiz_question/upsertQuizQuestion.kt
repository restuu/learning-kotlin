package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.model.QuizQuestion
import com.restuu.presentation.config.quizQuestions
import io.ktor.server.request.receive
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.upsertQuizQuestion() {
    post(path = "/quiz/questions") {
        val question = call.receive<QuizQuestion>()
        quizQuestions.add(question)
        call.respondText("Question saved")
    }
}