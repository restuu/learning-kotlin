package com.restuu.quiz.question

import com.restuu.quiz.question.dto.QuestionRequestDTO
import com.restuu.quiz.question.dto.QuestionResponseDTO
import com.restuu.quiz.shared.dto.response.ApiResponse
import com.restuu.quiz.shared.dto.response.HTTPResponse
import com.restuu.quiz.shared.dto.response.toResponseEntity
import jakarta.validation.Valid
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/quiz/questions")
class QuestionController(
    private val questionService: QuestionService
) {

    @PostMapping("")
    suspend fun upsertQuestion(
        @Valid @RequestBody question: QuestionRequestDTO
    ): HTTPResponse<Unit> {
        questionService.upsertQuestion(question)

        return ApiResponse.success<Unit>().toResponseEntity()
    }

    @GetMapping("")
    suspend fun getAllQuestions(
        topicCode: Int?,
        limit: Int
    ): HTTPResponse<List<QuestionResponseDTO>> {
        return questionService
            .findAllQuestions(topicCode, limit)
            .toList(ArrayList())
            .let { ApiResponse.success(it).toResponseEntity() }
    }
}