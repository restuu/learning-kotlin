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
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class QuizQuestionRepositoryImpl(mongoDb: MongoDatabase) : QuizQuestionRepository {
    private val questionCollection = mongoDb.getCollection<QuizQuestionEntity>(MONGO_COLLECTION_NAME_QUIZ_QUESTIONS)

    override suspend fun upsertQuestion(question: QuizQuestion): Result<Boolean, DataError> {
        val questionID = question.id

        if (questionID == null) {
            return insertOne(question)
        }

        return updateById(questionID, question)
    }

    override suspend fun getAllQuizQuestions(
        topicCode: Int?,
        limit: Int
    ): Result<List<QuizQuestion>, DataError> {
        return try {
            val filterQuery = topicCode
                ?.let { eq(QuizQuestionEntity::topicCode.name, it) }
                ?: empty()

            questionCollection
                .find(filterQuery)
                .limit(limit)
                .map { it.toQuizQuestion() }
                .toList()
                .let {
                    if (it.isEmpty()) return@let Result.Failure(DataError.NotFound)
                    Result.Success(it)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun getQuestionById(id: String): Result<QuizQuestion, DataError> {
        return try {
            val filterQuery = eq(QuizQuestionEntity::_id.name, ObjectId(id))

            questionCollection
                .find(filterQuery)
                .firstOrNull()
                ?.let { Result.Success(it.toQuizQuestion()) }
                ?: Result.Failure(DataError.NotFound)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteQuestionById(id: String): Result<Boolean, DataError> {
        return try {
            val filterQuery = eq(QuizQuestionEntity::_id.name, ObjectId(id))

            questionCollection
                .deleteOne(filterQuery)
                .let {
                    if (it.wasAcknowledged() && it.deletedCount > 0) return@let Result.Success(true)
                    else Result.Failure(DataError.NotFound)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun bulkInsertQuestions(questions: List<QuizQuestion>): Result<Unit, DataError> {
        return runCatching {
            val mappedQuestions = questions
                .map { it.toQuizQuestionEntity() }

            questionCollection
                .insertMany(mappedQuestions)

            Result.Success(Unit)
        }
            .getOrElse { e ->
                e.printStackTrace()
                Result.Failure(DataError.Database)
            }
    }

    suspend fun insertOne(question: QuizQuestion): Result<Boolean, DataError> {
        return try {
            questionCollection
                .insertOne(question.toQuizQuestionEntity())
                .let {
                    if (it.insertedId != null) return@let Result.Success(true)
                    Result.Failure(DataError.Database)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    suspend fun updateById(id: String, question: QuizQuestion): Result<Boolean, DataError> {
        return try {
            val filter = eq(QuizQuestionEntity::_id.name, ObjectId(id))

            val updates = combine(
                set(QuizQuestionEntity::question.name, question.question),
                set(QuizQuestionEntity::correctAnswer.name, question.correctAnswer),
                set(QuizQuestionEntity::incorrectAnswers.name, question.incorrectAnswers),
                set(QuizQuestionEntity::explanation.name, question.explanation),
                set(QuizQuestionEntity::topicCode.name, question.topicCode),
            )

            questionCollection
                .updateOne(filter, updates)
                .let {
                    if (it.matchedCount > 0) return@let Result.Success(true)
                    Result.Failure(DataError.NotFound)
                }
        } catch (e: Exception) {
            e.printStackTrace()
            return Result.Failure(DataError.Database)
        }
    }
}