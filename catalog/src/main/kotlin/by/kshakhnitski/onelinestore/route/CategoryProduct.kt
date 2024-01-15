package by.kshakhnitski.onelinestore.route

import by.kshakhnitski.onelinestore.service.ProductService
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.categoryProductRouting() {
    val productService: ProductService by inject()

    route("/categories/{categoryId}/products") {
        get {
            val categoryId = call.parameters["categoryId"]?.toLongOrNull()
                ?: throw BadRequestException("Category id [${call.parameters["categoryId"]}] is not valid")

            call.respond(productService.getAll(categoryId = categoryId))
        }
    }
}