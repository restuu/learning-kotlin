package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.upsertQuizQuestion(quizQuestionRepository: QuizQuestionRepository) {
    post(path = "/quiz/questions") {
        val question = call.receive<QuizQuestion>()
        if (quizQuestionRepository.upsertQuestion(question)) {
            call.respond(message = "Question saved", status = HttpStatusCode.Created)
        } else {
            call.respond(message = "Question not saved", status = HttpStatusCode.InternalServerError)
        }
    }
}