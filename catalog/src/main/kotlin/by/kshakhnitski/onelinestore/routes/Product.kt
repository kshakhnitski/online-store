package by.kshakhnitski.onelinestore.routes

import by.kshakhnitski.onelinestore.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.dto.ProductUpdateRequest
import by.kshakhnitski.onelinestore.exception.ValidationException
import by.kshakhnitski.onelinestore.service.ProductService
import by.kshakhnitski.onelinestore.service.impl.ProductServiceImpl
import by.kshakhnitski.onelinestore.validator.productCreateRequestValidator
import by.kshakhnitski.onelinestore.validator.productUpdateRequestValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Routing.productRouting() {
    val productService: ProductService = ProductServiceImpl()
    route("/products") {
        get {
            call.respond(productService.getAll())
        }
        post {
            val createRequest = call.receive<ProductCreateRequest>()
            productCreateRequestValidator.validate(createRequest).apply {
                if (errors.isNotEmpty()) throw ValidationException(errors)
            }
            call.respond(productService.create(createRequest))
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Product id [${call.parameters["id"]}] is not valid")
            call.respond(HttpStatusCode.Created, productService.getById(id))
        }
        patch("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Product id [${call.parameters["id"]}] is not valid")
            val updateRequest = call.receive<ProductUpdateRequest>()
            productUpdateRequestValidator.validate(updateRequest).apply {
                if (errors.isNotEmpty()) throw ValidationException(errors)
            }
            call.respond(productService.update(id, updateRequest))
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("Product id [${call.parameters["id"]}] is not valid")
            productService.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}