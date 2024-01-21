package by.kshakhnitski.onelinestore.catalog.module

import by.kshakhnitski.onelinestore.catalog.dto.ApiError
import by.kshakhnitski.onelinestore.catalog.dto.ValidationApiError
import by.kshakhnitski.onelinestore.catalog.dto.ValidationErrorItem
import by.kshakhnitski.onelinestore.catalog.exception.ValidationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        status(HttpStatusCode.InternalServerError) { call, status ->
            call.respond(
                status, buildApiError(
                    status = status,
                    message = "Some error occurred"
                )
            )
        }
        exception<NotFoundException> { call, cause ->
            val status = HttpStatusCode.NotFound
            call.respond(
                status, buildApiError(
                    status = status,
                    message = cause.message ?: "Not Found"
                )
            )
        }
        exception<BadRequestException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(
                status, buildApiError(
                    status = status,
                    message = cause.message ?: "Bad Request"
                )
            )
        }
        exception<ValidationException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(
                status, buildValidationApiError(
                    status = status,
                    message = "Validation Error",
                    errors = cause.errors
                )
            )
        }
    }
}

private fun buildApiError(status: HttpStatusCode, message: String) =
    ApiError(
        code = status.value,
        status = status.description,
        message = message
    )

private fun buildValidationApiError(status: HttpStatusCode, message: String, errors: List<ValidationErrorItem>) =
    ValidationApiError(
        code = status.value,
        status = status.description,
        message = message,
        validationErrors = errors
    )