package com.restuu.presentation.routes.issue_report

import com.restuu.domain.repository.IssueReportRepository
import com.restuu.domain.util.onFailure
import com.restuu.domain.util.onSuccess
import com.restuu.presentation.util.respondWithError
import io.ktor.resources.Resource
import io.ktor.server.request.receive
import io.ktor.server.resources.delete
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route

@Resource("/report/issues")
class IssueReports {
    @Resource("{reportId}")
    class ById(
        val parent: IssueReports = IssueReports(),
        val reportId: String,
    )
}

fun Route.issueReportRoutes(
    issueReportRepository: IssueReportRepository,
) {
    post<IssueReports> {
        issueReportRepository
            .insertIssueReport(call.receive())
            .onSuccess { call.respond(message = "Issue report saved", status = io.ktor.http.HttpStatusCode.Created) }
            .onFailure { respondWithError(it) }
    }

    get<IssueReports> {
        issueReportRepository
            .getAllIssueReports()
            .onSuccess { call.respond(it) }
            .onFailure { respondWithError(it) }
    }

    delete<IssueReports.ById> { report ->
        issueReportRepository
            .deleteIssueReportById(report.reportId)
            .onSuccess { call.respondText("Issue report removed") }
            .onFailure { respondWithError(it) }
    }
}