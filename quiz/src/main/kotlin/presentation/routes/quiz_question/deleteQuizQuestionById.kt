package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.server.resources.delete
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route

fun Route.deleteQuizQuestionById(
    quizQuestionRepository: QuizQuestionRepository
) {
    delete<QuizQuestionRoutesPath.ById> { path ->
        val questionId: String = path.questionId

        quizQuestionRepository.deleteQuestionById(questionId)
            .onSuccess { call.respondText("Question removed") }
            .onFailure { respondWithError(it) }
    }
}