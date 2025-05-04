package com.restuu.domain.repository

import com.restuu.domain.model.QuizQuestion

interface QuizQuestionRepository {
    fun upsertQuestion(question: QuizQuestion): Boolean
    fun getAllQuizQuestions(topicCode: Int?, limit: Int?): List<QuizQuestion>
    fun getQuestionById(id: String): QuizQuestion?
    fun deleteQuestionById(id: String): Boolean
}