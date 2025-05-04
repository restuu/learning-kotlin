package com.restuu.domain.data.repository

import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl : QuizQuestionRepository {
    private val questions = mutableListOf<QuizQuestion>()

    override fun upsertQuestion(question: QuizQuestion): Boolean {
        return questions.add(question)
    }

    override fun getAllQuizQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        return questions.filter { topicCode == null || it.topicCode == topicCode }
            .take(limit ?: 10)
    }

    override fun getQuestionById(id: String): QuizQuestion? {
        return questions.find { it.id == id }
    }

    override fun deleteQuestionById(id: String): Boolean {
        return questions.removeIf { it.id == id }
    }
}