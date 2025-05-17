package com.restuu.domain.repository

import com.restuu.domain.model.QuizTopic
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result

interface QuizTopicRepository {
    suspend fun getAllTopics(limit: Int): Result<List<QuizTopic>, DataError>
    suspend fun upsertTopic(topic: QuizTopic): Result<Unit, DataError>
    suspend fun getTopicById(id: String): Result<QuizTopic, DataError>
    suspend fun deleteTopicById(id: String): Result<Unit, DataError>
}