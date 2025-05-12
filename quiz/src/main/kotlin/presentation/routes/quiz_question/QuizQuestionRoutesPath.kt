package com.restuu.presentation.routes.quiz_question

import io.ktor.resources.Resource

@Resource(path = "/quiz/questions")
class QuizQuestionRoutesPath(
    val topicCode: Int? = null,
    val limit: Int = 10,
) {
    @Resource(path = "/{questionId}")
    data class ById(
        val parent: QuizQuestionRoutesPath = QuizQuestionRoutesPath(),
        val questionId: String
    )
}