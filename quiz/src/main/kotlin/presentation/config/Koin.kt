package com.restuu.presentation.config

import com.restuu.domain.data.database.DatabaseFactory
import com.restuu.domain.data.repository.IssueReportRepositoryImpl
import com.restuu.domain.data.repository.QuizQuestionRepositoryImpl
import com.restuu.domain.data.repository.QuizTopicRepositoryImpl
import com.restuu.domain.repository.IssueReportRepository
import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.repository.QuizTopicRepository
import io.ktor.server.application.Application
import io.ktor.server.application.install
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()

        modules(module {
            single { DatabaseFactory.create() }
            singleOf(::QuizQuestionRepositoryImpl).bind<QuizQuestionRepository>()
            singleOf(::QuizTopicRepositoryImpl).bind<QuizTopicRepository>()
            singleOf(::IssueReportRepositoryImpl).bind<IssueReportRepository>()
        })
    }
}