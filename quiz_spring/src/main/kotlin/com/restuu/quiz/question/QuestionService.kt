package com.restuu.quiz.question

import com.restuu.quiz.question.dto.QuestionRequestDTO
import com.restuu.quiz.question.dto.QuestionResponseDTO
import com.restuu.quiz.question.mapper.toQuestion
import com.restuu.quiz.question.mapper.toQuestionResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class QuestionService(
    private val questionRepository: QuestionRepository,
) {
    fun findAllQuestions(topicCode: Int?, limit: Int): Flow<QuestionResponseDTO> {
        val questions = if (topicCode == null) {
            questionRepository.findAllBy(PageRequest.of(0, limit))
        } else questionRepository.findByTopicCode(topicCode, PageRequest.of(0, limit))

        return questions.map { it.toQuestionResponseDTO() }
    }

    suspend fun upsertQuestion(question: QuestionRequestDTO) {
        questionRepository.save(question.toQuestion())
    }
}