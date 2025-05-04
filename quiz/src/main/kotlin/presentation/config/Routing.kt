package com.restuu.presentation.config

import com.restuu.domain.model.QuizQuestion
import com.restuu.presentation.routes.quiz_question.deleteQuizQuestionById
import com.restuu.presentation.routes.quiz_question.getAllQuizQuestions
import com.restuu.presentation.routes.quiz_question.getQuizQuestionById
import com.restuu.presentation.routes.quiz_question.upsertQuizQuestion
import com.restuu.presentation.routes.root
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {
    routing {
        root()
        getAllQuizQuestions()
        upsertQuizQuestion()
        deleteQuizQuestionById()
        getQuizQuestionById()
    }
}

val quizQuestions = mutableListOf<QuizQuestion>()