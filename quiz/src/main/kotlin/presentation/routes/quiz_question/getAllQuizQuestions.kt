package com.restuu.presentation.routes.quiz_question

import com.restuu.domain.model.QuizQuestion
import io.ktor.server.response.respond
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.getAllQuizQuestions() {
    get(path = "/quiz/questions") {
        val question = QuizQuestion(
            question = "Which company developed Kotlin?",
            correctAnswer = "JetBrains",
            incorrectAnswers = listOf("Google", "Microsoft", "Amazon"),
            explanation = "JetBrains is a leading developer of Kotlin.",
            topicCode = 1
        )

        call.respond(question)
    }
}