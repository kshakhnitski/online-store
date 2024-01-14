package by.kshakhnitski.onelinestore.plugins

import by.kshakhnitski.onelinestore.routes.categoryRouting
import by.kshakhnitski.onelinestore.routes.productRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryRouting()
        productRouting()
    }
}
