package com.restuu.presentation

import com.restuu.presentation.config.configureKoin
import com.restuu.presentation.config.configureLogging
import com.restuu.presentation.config.configureRouting
import com.restuu.presentation.config.configureSerialization
import com.restuu.presentation.config.configureStatusPages
import com.restuu.presentation.config.configureValidation
import io.ktor.server.application.Application
import io.ktor.server.netty.EngineMain

fun main(args: Array<String>) {
    EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureKoin()
    configureLogging()
    configureSerialization()
    configureStatusPages()
    configureValidation()
    configureRouting()
}
