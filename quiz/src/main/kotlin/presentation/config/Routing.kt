package com.restuu.presentation.config

import com.restuu.domain.repository.IssueReportRepository
import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.repository.QuizTopicRepository
import com.restuu.presentation.routes.issue_report.issueReportRoutes
import com.restuu.presentation.routes.quiz_question.deleteQuizQuestionById
import com.restuu.presentation.routes.quiz_question.getAllQuizQuestions
import com.restuu.presentation.routes.quiz_question.getQuizQuestionById
import com.restuu.presentation.routes.quiz_question.upsertQuizQuestion
import com.restuu.presentation.routes.quiz_topic.quizTopicRoutes
import com.restuu.presentation.routes.root
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.http.content.staticResources
import io.ktor.server.resources.Resources
import io.ktor.server.routing.routing
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    install(Resources)

    val quizQuestionRepository: QuizQuestionRepository by inject()
    val quizTopicRepository: QuizTopicRepository by inject()
    val issueReportRepository: IssueReportRepository by inject()

    routing {
        root()
        getAllQuizQuestions(quizQuestionRepository)
        upsertQuizQuestion(quizQuestionRepository)
        deleteQuizQuestionById(quizQuestionRepository)
        getQuizQuestionById(quizQuestionRepository)

        quizTopicRoutes(quizTopicRepository)
        issueReportRoutes(issueReportRepository)

        staticResources(
            remotePath = "/images",
            basePackage = "images"
        )
    }
}
