package com.restuu.domain.data.mapper

import com.restuu.domain.data.database.entity.IssueReportEntity
import com.restuu.domain.model.IssueReport

fun IssueReport.toIssueReportEntity(): IssueReportEntity = IssueReportEntity(
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestamp
)

fun IssueReportEntity.toIssueReport(): IssueReport = IssueReport(
    id = _id.toString(),
    questionId = questionId,
    issueType = issueType,
    additionalComment = additionalComment,
    userEmail = userEmail,
    timestamp = timestamp
)