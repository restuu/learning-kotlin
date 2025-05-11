package com.restuu.domain.repository

import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result

interface QuizQuestionRepository {
    suspend fun upsertQuestion(question: QuizQuestion): Result<Boolean, DataError>
    suspend fun getAllQuizQuestions(topicCode: Int?, limit: Int): Result<List<QuizQuestion>, DataError>
    suspend fun getQuestionById(id: String): Result<QuizQuestion, DataError>
    suspend fun deleteQuestionById(id: String): Result<Boolean, DataError>
}