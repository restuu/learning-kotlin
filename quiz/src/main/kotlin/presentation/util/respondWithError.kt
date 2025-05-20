package com.restuu.presentation.util

import com.restuu.domain.util.DataError
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import io.ktor.server.routing.RoutingContext

suspend fun RoutingContext.respondWithError(error: DataError) {
    when (error) {
        DataError.NotFound -> call.respond(
            message = "Data not found",
            status = HttpStatusCode.NotFound,
        )
//        DataError.Validation -> TODO()
//        DataError.Database -> TODO()
//        DataError.Unknown -> TODO()
        else -> call.respond(
            message = "Unknown error",
            status = HttpStatusCode.InternalServerError,
        )
    }
}
