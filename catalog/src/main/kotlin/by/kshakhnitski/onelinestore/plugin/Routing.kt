package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.route.categoryRouting
import by.kshakhnitski.onelinestore.route.productRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        categoryRouting()
        productRouting()
    }
}
