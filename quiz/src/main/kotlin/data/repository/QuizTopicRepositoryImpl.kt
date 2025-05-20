package com.restuu.domain.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.restuu.domain.data.database.entity.QuizTopicEntity
import com.restuu.domain.data.mapper.toQuizTopic
import com.restuu.domain.data.mapper.toQuizTopicEntity
import com.restuu.domain.data.util.Constant
import com.restuu.domain.model.QuizTopic
import com.restuu.domain.repository.QuizTopicRepository
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class QuizTopicRepositoryImpl(
    mongoDb: MongoDatabase
) : QuizTopicRepository {

    private val topicCollection = mongoDb
        .getCollection<QuizTopicEntity>(Constant.MONGO_COLLECTION_NAME_QUIZ_TOPICS)

    override suspend fun getAllTopics(limit: Int): Result<List<QuizTopic>, DataError> {
        return orErrorDatabase {
            topicCollection
                .find()
                .limit(limit)
                .map { it.toQuizTopic() }
                .toList()
                .let {
                    if (it.isEmpty()) return@let Result.Failure(DataError.NotFound)
                    Result.Success(it)
                }
        }
    }

    override suspend fun upsertTopic(topic: QuizTopic): Result<Unit, DataError> {
        val id = topic.id

        if (id == null) {
            return insertOne(topic)
        }
        return updateById(id, topic)
    }

    override suspend fun getTopicById(id: String): Result<QuizTopic, DataError> {
        return orErrorDatabase {
            val objId = ObjectId(id)
            val filter = Filters.eq(QuizTopicEntity::_id.name, objId)

            topicCollection
                .find(filter)
                .firstOrNull()
                ?.let { Result.Success(it.toQuizTopic()) }
                ?: Result.Failure(DataError.NotFound)
        }
    }

    override suspend fun deleteTopicById(id: String): Result<Unit, DataError> {
        return orErrorDatabase {
            val objId = ObjectId(id)

            val filter = Filters.eq(QuizTopicEntity::_id.name, objId)

            topicCollection
                .deleteOne(filter)
                .let {
                    if (it.deletedCount > 0) return@let Result.Success(Unit)
                    Result.Failure(DataError.NotFound)
                }
        }
    }

    private suspend fun insertOne(topic: QuizTopic): Result<Unit, DataError> {
        return orErrorDatabase {
            topicCollection
                .insertOne(topic.toQuizTopicEntity())
                .let {
                    if (it.insertedId != null) return@let Result.Success(Unit)
                    return Result.Failure(DataError.Database)
                }
        }
    }

    private suspend fun updateById(
        id: String,
        topic: QuizTopic
    ): Result<Unit, DataError> {
        return orErrorDatabase {
            val objId = ObjectId(id)
            val filter = Filters.eq(QuizTopicEntity::_id.name, objId)
            val updates = Updates.combine(
                Updates.set(QuizTopicEntity::name.name, topic.name),
                Updates.set(QuizTopicEntity::code.name, topic.code),
                Updates.set(QuizTopicEntity::imageUrl.name, topic.imageUrl),
            )

            topicCollection
                .updateOne(filter, updates)
                .let {
                    if (it.matchedCount > 0) return@let Result.Success(Unit)
                    Result.Failure(DataError.NotFound)
                }
        }
    }

    private inline fun <T> orErrorDatabase(fetch: QuizTopicRepositoryImpl.() -> Result<T, DataError>): Result<T, DataError> {
        return try {
            fetch()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }
}