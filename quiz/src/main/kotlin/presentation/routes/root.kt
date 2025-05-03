package com.restuu.presentation.routes

import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get

fun Route.root() {
    get(path = "/") {
        call.respondText("Welcome to Quiz API!")
    }
}