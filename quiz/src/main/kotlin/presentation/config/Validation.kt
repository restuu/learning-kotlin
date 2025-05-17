package com.restuu.presentation.config

import com.restuu.presentation.validator.validateQuizQuestion
import com.restuu.presentation.validator.validateQuizTopic
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.requestvalidation.RequestValidation

fun Application.configureValidation() {
    install(RequestValidation) {
        validateQuizQuestion()
        validateQuizTopic()
    }
}