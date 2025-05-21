package presentation.routes.quiz_question

import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.http.HttpStatusCode
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route

@Resource("/quiz/questions")
class QuizQuestions(
    val topicCode: Int? = null,
    val limit: Int = 10,
) {
    @Resource(path = "/{questionId}")
    data class ById(
        val parent: QuizQuestions = QuizQuestions(),
        val questionId: String
    )
}

fun Route.quizQuestionRoutes(quizQuestionRepository: QuizQuestionRepository) {
    post<QuizQuestions> {
        val question = call.receive<QuizQuestion>()
        quizQuestionRepository
            .upsertQuestion(question)
            .onSuccess { call.respond(message = "Question saved", status = HttpStatusCode.Created) }
            .onFailure { respondWithError(it) }
    }

    get<QuizQuestions> { path ->
        val limit = path.limit
        val topicCode = path.topicCode

        quizQuestionRepository.getAllQuizQuestions(topicCode, limit)
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }

    }

    get<QuizQuestions.ById> { path ->
        val questionId = path.questionId

        quizQuestionRepository.getQuestionById(questionId)
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }
    }

    delete<QuizQuestions.ById> { path ->
        val questionId: String = path.questionId

        quizQuestionRepository.deleteQuestionById(questionId)
            .onSuccess { call.respondText("Question removed") }
            .onFailure { respondWithError(it) }
    }
}