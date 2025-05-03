package com.restuu

import com.restuu.plugins.configureFrameworks
import com.restuu.plugins.configureRouting
import com.restuu.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

@Suppress("unused")
fun Application.module() {
    configureFrameworks()
    configureSerialization()
    configureRouting()
}
