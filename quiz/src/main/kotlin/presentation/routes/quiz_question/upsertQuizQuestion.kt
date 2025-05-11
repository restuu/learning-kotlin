package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.post

fun Route.upsertQuizQuestion(quizQuestionRepository: QuizQuestionRepository) {
    post(path = "/quiz/questions") {
        val question = call.receive<QuizQuestion>()
        quizQuestionRepository
            .upsertQuestion(question)
            .onSuccess { call.respond(message = "Question saved", status = HttpStatusCode.Created) }
            .onFailure { ::respondWithError }
        }
    }
