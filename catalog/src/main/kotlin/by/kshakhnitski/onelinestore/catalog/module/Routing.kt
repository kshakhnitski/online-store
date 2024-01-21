package by.kshakhnitski.onelinestore.catalog.module

import by.kshakhnitski.onelinestore.catalog.route.categoryProductRouting
import by.kshakhnitski.onelinestore.catalog.route.categoryRouting
import by.kshakhnitski.onelinestore.catalog.route.productRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryRouting()
        productRouting()
        categoryProductRouting()
    }
}
