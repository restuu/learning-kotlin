package com.restuu.domain.data.database

import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.restuu.domain.data.util.Constant.MONGO_DB_NAME
import kotlinx.coroutines.runBlocking
import org.bson.BsonInt64
import org.bson.Document

object DatabaseFactory {
    fun create(): MongoDatabase {
        val uri = System.getenv("MONGO_URI") ?: throw IllegalArgumentException("MONGO_URI env var not set")
        val mongoClient = MongoClient.create(uri)

        val db = mongoClient.getDatabase(MONGO_DB_NAME)

        runBlocking {
            db.runCommand(Document("ping", BsonInt64(1)))
        }
        return db
    }
}