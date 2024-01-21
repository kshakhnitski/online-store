package by.kshakhnitski.onelinestore.user.module

import by.kshakhnitski.onelinestore.user.dto.ApiError
import by.kshakhnitski.onelinestore.user.dto.ValidationApiError
import by.kshakhnitski.onelinestore.user.dto.ValidationErrorItem
import by.kshakhnitski.onelinestore.user.exception.ConflictException
import by.kshakhnitski.onelinestore.user.exception.InvalidCredentialsException
import by.kshakhnitski.onelinestore.user.exception.JwtValidationException
import by.kshakhnitski.onelinestore.user.exception.ValidationException
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
        exception<ConflictException> { call, cause ->
            val status = HttpStatusCode.Conflict
            call.respond(
                status, buildApiError(
                    status = status,
                    message = cause.message ?: "Conflict"
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
        exception<InvalidCredentialsException> { call, cause ->
            val status = HttpStatusCode.Unauthorized
            call.respond(
                status, buildApiError(
                    status = status,
                    message = cause.message ?: "Invalid Credentials"
                )
            )
        }
        exception<JwtValidationException> {call, cause ->
            val status = HttpStatusCode.Unauthorized
            call.respond(
                status, buildApiError(
                    status = status,
                    message = cause.message ?: "JWT is not valid"
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