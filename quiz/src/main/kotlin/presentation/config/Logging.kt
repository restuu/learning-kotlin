package com.restuu.presentation.config

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.calllogging.CallLogging
import org.slf4j.event.Level

fun Application.configureLogging() {
    install(CallLogging) {
        level = Level.DEBUG
    }
}