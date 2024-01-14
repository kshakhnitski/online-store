package by.kshakhnitski.onelinestore.routes

import by.kshakhnitski.onelinestore.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.dto.CategoryUpdateRequest
import by.kshakhnitski.onelinestore.exception.ValidationException
import by.kshakhnitski.onelinestore.service.CategoryService
import by.kshakhnitski.onelinestore.validator.categoryCreateRequestValidator
import by.kshakhnitski.onelinestore.validator.categoryUpdateRequestValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.categoryRouting() {
    val categoryService: CategoryService by inject()
    route("/categories") {
        get {
            call.respond(categoryService.getAll())
        }
        post {
            val createRequest = call.receive<CategoryCreateRequest>()
            categoryCreateRequestValidator.validate(createRequest).apply {
                if (errors.isNotEmpty()) throw ValidationException(errors)
            }
            call.respond(HttpStatusCode.Created, categoryService.create(createRequest))
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Category id [${call.parameters["id"]}] is not valid")
            call.respond(categoryService.getById(id))
        }
        patch("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Category id [${call.parameters["id"]}] is not valid")
            val updateRequest = call.receive<CategoryUpdateRequest>()
            categoryUpdateRequestValidator.validate(updateRequest).apply {
                if (errors.isNotEmpty()) throw ValidationException(errors)
            }
            call.respond(categoryService.update(id, updateRequest))
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Category id [${call.parameters["id"]}] is not valid")
            categoryService.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}