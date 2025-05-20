package com.restuu.presentation.validator

import com.restuu.domain.model.IssueReport
import io.ktor.server.plugins.requestvalidation.RequestValidationConfig
import io.ktor.server.plugins.requestvalidation.ValidationResult

fun RequestValidationConfig.validateIssueReport() {
    validate<IssueReport> { report ->
        when {
            report.questionId.isBlank() ->
                ValidationResult.Invalid("Question ID cannot be blank")

            report.issueType.isBlank() ->
                ValidationResult.Invalid("Issue type cannot be blank")

            report.timestamp.isEmpty() ->
                ValidationResult.Invalid("Timestamp cannot be empty")

            report.additionalComment != null && report.additionalComment.length < 5 ->
                ValidationResult.Invalid("Additional comment must be at least 5 characters long")

            report.userEmail != null && !report.userEmail.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) ->
                ValidationResult.Invalid("Invalid email address")

            else -> ValidationResult.Valid
        }
    }
}