package com.restuu.presentation.routes.quiz_topic

import com.restuu.domain.model.QuizTopic
import com.restuu.domain.repository.QuizTopicRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route

@Resource("/quiz/topics")
class QuizTopics(
    val limit: Int = 10,
) {
    @Resource("/{topicId}")
    data class ById(
        val parent: QuizTopics = QuizTopics(),
        val topicId: String,
    )
}

fun Route.quizTopicRoutes(
    quizTopicRepository: QuizTopicRepository
) {
    get<QuizTopics> { quizTopic ->
        quizTopicRepository
            .getAllTopics(quizTopic.limit)
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }
    }

    post<QuizTopics> {
        val topic = call.receive<QuizTopic>()

        quizTopicRepository
            .upsertTopic(topic)
            .onSuccess { call.respondText(text = "Topic saved") }
            .onFailure { respondWithError(it) }
    }

    get<QuizTopics.ById> { quizTopic ->
        quizTopicRepository.getTopicById(quizTopic.topicId)
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }
    }

    delete<QuizTopics.ById> { quizTopic ->
        quizTopicRepository.deleteTopicById(quizTopic.topicId)
            .onSuccess { call.respondText(text = "Topic deleted") }
            .onFailure { respondWithError(it) }
    }
}