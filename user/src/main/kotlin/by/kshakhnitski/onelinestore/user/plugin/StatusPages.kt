package by.kshakhnitski.onelinestore.user.plugin

import by.kshakhnitski.onelinestore.user.dto.ApiError
import by.kshakhnitski.onelinestore.user.dto.ValidationApiError
import by.kshakhnitski.onelinestore.user.dto.ValidationErrorItem
import by.kshakhnitski.onelinestore.user.exception.ConflictException
import by.kshakhnitski.onelinestore.user.exception.InvalidCredentialsException
import by.kshakhnitski.onelinestore.user.exception.ValidationException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<NotFoundException> { call, cause ->
            val status = HttpStatusCode.NotFound
            call.respond(status, buildApiError(status, "Not Found", cause))
        }
        exception<Throwable> { call, cause ->
            val status = HttpStatusCode.InternalServerError
            call.respond(status, buildApiError(status, "Internal Server Error", cause))
        }
        exception<BadRequestException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(status, buildApiError(status, "Bad Request", cause))
        }
        exception<ConflictException> { call, cause ->
            val status = HttpStatusCode.Conflict
            call.respond(status, buildApiError(status, "Conflict", cause))
        }
        exception<ValidationException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(status, buildValidationApiError(status, "Validation Error", cause))
        }
        exception<InvalidCredentialsException> { call, cause ->
            val status = HttpStatusCode.Unauthorized
            call.respond(status, buildApiError(status, "Invalid Credentials", cause))
        }
    }
}

private fun buildApiError(status: HttpStatusCode, defaultMessage: String, cause: Throwable) =
    ApiError(
        code = status.value,
        status = status.description,
        message = cause.message ?: defaultMessage
    )

private fun buildValidationApiError(status: HttpStatusCode, defaultMessage: String, cause: ValidationException) =
    ValidationApiError(
        code = status.value,
        status = status.description,
        message = defaultMessage,
        validationErrors = cause.errors.map {
            ValidationErrorItem(
                field = it.dataPath.substring(1),
                message = it.message
            )
        }
    )