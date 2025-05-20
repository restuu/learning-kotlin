package com.restuu.domain.data.database.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class QuizQuestionEntity(
    @BsonId
    val _id: ObjectId = ObjectId(),
    val question: String,
    val correctAnswer: String,
    val incorrectAnswers: List<String>,
    val explanation: String,
    val topicCode: Int,
)
