package by.kshakhnitski.onelinestore.catalog.route

import by.kshakhnitski.onelinestore.catalog.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.ProductUpdateRequest
import by.kshakhnitski.onelinestore.catalog.service.ProductService
import by.kshakhnitski.onelinestore.catalog.validator.ProductCreateRequestValidator
import by.kshakhnitski.onelinestore.catalog.validator.ProductUpdateRequestValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.productRouting() {
    val productService: ProductService by inject()
    val productCreateRequestValidator: ProductCreateRequestValidator by inject()
    val productUpdateRequestValidator: ProductUpdateRequestValidator by inject()

    route("/products") {
        get {
            call.respond(productService.getAll())
        }
        post {
            val createRequest = call.receive<ProductCreateRequest>()
            productCreateRequestValidator.validate(createRequest)
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
            productUpdateRequestValidator.validate(updateRequest)
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