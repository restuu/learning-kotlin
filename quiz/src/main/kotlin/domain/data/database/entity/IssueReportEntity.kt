package com.restuu.domain.data.database.entity

import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId

data class IssueReportEntity(
    @BsonId
    val _id: ObjectId? = null,
    val questionId: String,
    val issueType: String,
    val additionalComment: String?,
    val userEmail: String?,
    val timestamp: String,
)
