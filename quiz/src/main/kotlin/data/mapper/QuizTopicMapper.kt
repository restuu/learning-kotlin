package com.restuu.domain.data.mapper

import com.restuu.domain.data.database.entity.QuizTopicEntity
import com.restuu.domain.model.QuizTopic

fun QuizTopic.toQuizTopicEntity(): QuizTopicEntity = QuizTopicEntity(
    name = name,
    imageUrl = imageUrl,
    code = code
)

fun QuizTopicEntity.toQuizTopic(): QuizTopic = QuizTopic(
    id = _id?.toString() ?: "",
    name = name,
    imageUrl = imageUrl,
    code = code
)