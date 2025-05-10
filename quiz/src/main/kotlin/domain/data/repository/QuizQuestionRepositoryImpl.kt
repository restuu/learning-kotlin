package com.restuu.domain.data.repository

import com.mongodb.client.model.Filters.empty
import com.mongodb.client.model.Filters.eq
import com.mongodb.client.model.Updates.combine
import com.mongodb.client.model.Updates.set
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.restuu.domain.data.database.entity.QuizQuestionEntity
import com.restuu.domain.data.mapper.toQuizQuestion
import com.restuu.domain.data.mapper.toQuizQuestionEntity
import com.restuu.domain.data.util.Constant.MONGO_COLLECTION_NAME_QUIZ_QUESTIONS
import com.restuu.domain.model.QuizQuestion
import com.restuu.domain.repository.QuizQuestionRepository
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class QuizQuestionRepositoryImpl(mongoDb: MongoDatabase) : QuizQuestionRepository {
    private val questionCollection = mongoDb.getCollection<QuizQuestionEntity>(MONGO_COLLECTION_NAME_QUIZ_QUESTIONS)

    override suspend fun upsertQuestion(question: QuizQuestion): Boolean {
        return try {
            question.id
                ?.let {
                    val filter = eq(QuizQuestionEntity::_id.name, ObjectId(it))
                    val updates = combine(
                        set(QuizQuestionEntity::question.name, question.question),
                        set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                        set(QuizQuestionEntity::incorrectAnswers.name, question.incorrectAnswers),
                        set(QuizQuestionEntity::explanation.name, question.explanation),
                        set(QuizQuestionEntity::topicCode.name, question.topicCode),
                    )

                    questionCollection
                        .updateOne(filter, updates)
                        .wasAcknowledged()
                }
                ?: questionCollection
                    .insertOne(question.toQuizQuestionEntity())
                    .let { it.wasAcknowledged() && it.insertedId != null }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override suspend fun getAllQuizQuestions(
        topicCode: Int?,
        limit: Int?
    ): List<QuizQuestion> {
        val filterQuery = topicCode
            ?.let { eq(QuizQuestionEntity::topicCode.name, it) }
            ?: empty()

        return try {
            questionCollection
                .find(filterQuery)
                .limit(limit ?: 10)
                .map { it.toQuizQuestion() }
                .toList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override suspend fun getQuestionById(id: String): QuizQuestion? {
        val filterQuery = eq(QuizQuestionEntity::_id.name, ObjectId(id))

        return try {
            questionCollection
                .find(filterQuery)
                .firstOrNull()
                ?.toQuizQuestion()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override suspend fun deleteQuestionById(id: String): Boolean {
        val filterQuery = eq(QuizQuestionEntity::_id.name, ObjectId(id))
        return try {
            questionCollection
                .deleteOne(filterQuery)
                .deletedCount > 0L
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}