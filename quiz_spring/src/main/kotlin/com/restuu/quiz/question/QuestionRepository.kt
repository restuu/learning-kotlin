package com.restuu.quiz.question

import kotlinx.coroutines.flow.Flow
import org.bson.types.ObjectId
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QuestionRepository : CoroutineCrudRepository<Question, ObjectId> {

    fun findAllBy(page: Pageable): Flow<Question>
    fun findByTopicCode(topicCode: Int, page: Pageable): Flow<Question>
}