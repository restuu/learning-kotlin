package com.restuu.domain.repository

import com.restuu.domain.model.IssueReport
import com.restuu.domain.util.DataError
import com.restuu.domain.util.Result

interface IssueReportRepository {
    suspend fun getAllIssueReports(): Result<List<IssueReport>, DataError>
    suspend fun insertIssueReport(report: IssueReport): Result<Unit, DataError>
    suspend fun deleteIssueReportById(id: String): Result<Unit, DataError>
}