package com.restuu.domain.repository

import com.restuu.domain.model.QuizQuestion

interface QuizQuestionRepository {
    suspend fun upsertQuestion(question: QuizQuestion): Boolean
    suspend fun getAllQuizQuestions(topicCode: Int?, limit: Int?): List<QuizQuestion>
    suspend fun getQuestionById(id: String): QuizQuestion?
    suspend fun deleteQuestionById(id: String): Boolean
}