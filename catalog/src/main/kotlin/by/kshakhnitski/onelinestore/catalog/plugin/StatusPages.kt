package by.kshakhnitski.onelinestore.catalog.plugin

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
        exception<NotFoundException> { call, cause ->
            val status = HttpStatusCode.NotFound
            call.respond(
                status, ApiError(
                    code = status.value,
                    status = status.description,
                    message = cause.message ?: "Not Found"
                )
            )
        }
        exception<Throwable> { call, cause ->
            val status = HttpStatusCode.InternalServerError
            call.respond(
                status, ApiError(
                    code = status.value,
                    status = status.description,
                    message = cause.message ?: "Internal Server Error"
                )
            )
        }
        exception<BadRequestException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(
                status, ApiError(
                    code = status.value,
                    status = status.description,
                    message = cause.message ?: "Bad Request"
                )
            )
        }
        exception<ValidationException> { call, cause ->
            val status = HttpStatusCode.BadRequest
            call.respond(
                status, ValidationApiError(
                    code = status.value,
                    status = status.description,
                    message = "Validation Error",
                    validationErrors = cause.errors.map {
                        ValidationErrorItem(
                            field = it.dataPath.substring(1),
                            message = it.message
                        )
                    }
                )
            )
        }
    }
}