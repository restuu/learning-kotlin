package com.restuu.domain.data.repository

import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.restuu.domain.data.util.Constant.MONGO_COLLECTION_NAME_QUIZ_QUESTIONS
import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository

class QuizQuestionRepositoryImpl(mongoDb: MongoDatabase) : QuizQuestionRepository {
    private val questions = mutableListOf<QuizQuestion>()
    private val questionCollection = mongoDb.getCollection<QuizQuestion>(MONGO_COLLECTION_NAME_QUIZ_QUESTIONS)

    override suspend fun upsertQuestion(question: QuizQuestion): Boolean {
        val res = questionCollection.insertOne(question)

        return res.wasAcknowledged() && res.insertedId != null
    }

    override suspend fun getAllQuizQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        return questions.filter { topicCode == null || it.topicCode == topicCode }
            .take(limit ?: 10)
    }

    override suspend fun getQuestionById(id: String): QuizQuestion? {
        return questions.find { it.id == id }
    }

    override suspend fun deleteQuestionById(id: String): Boolean {
        return questions.removeIf { it.id == id }
    }
}