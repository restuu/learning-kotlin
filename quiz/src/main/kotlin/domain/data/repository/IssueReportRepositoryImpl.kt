package com.restuu.domain.data.repository

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.restuu.domain.data.database.entity.IssueReportEntity
import com.restuu.domain.data.mapper.toIssueReport
import com.restuu.domain.data.mapper.toIssueReportEntity
import com.restuu.domain.data.util.Constant.MONGO_COLLECTION_NAME_ISSUE_REPORTS
import com.restuu.domain.model.IssueReport
import com.restuu.domain.repository.IssueReportRepository
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.bson.types.ObjectId

class IssueReportRepositoryImpl(
    mongoDb: MongoDatabase,
): IssueReportRepository {

    val issueReportCollection = mongoDb
        .getCollection<IssueReportEntity>(MONGO_COLLECTION_NAME_ISSUE_REPORTS)

    override suspend fun getAllIssueReports(): Result<List<IssueReport>, DataError> {
        return try {
            issueReportCollection
                .find()
                .map {it.toIssueReport()}
                .toList()
                .let {
                    if (it.isEmpty()) return@let Result.Failure(DataError.NotFound)
                    Result.Success(it)
                }
        }catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError> {
        return try {
            issueReportCollection.insertOne(report.toIssueReportEntity())

            Result.Success(Unit)
        } catch (e: Exception) {
            e.printStackTrace()
            Result.Failure(DataError.Database)
        }
    }

    override suspend fun deleteIssueReportById(id: String): Result<Unit, DataError> {
        return runCatching {
            val filter = Filters.eq(IssueReportEntity::_id.name, ObjectId(id))

            issueReportCollection
                .deleteOne(filter)
        }
            .map {
                if (it.deletedCount > 0) return@map Result.Success(Unit)
                Result.Failure(DataError.NotFound)
            }
            .getOrElse {
                it.printStackTrace()
                Result.Failure(DataError.Database)
            }
    }
}