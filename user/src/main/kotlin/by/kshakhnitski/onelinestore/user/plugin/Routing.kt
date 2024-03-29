package by.kshakhnitski.onelinestore.user.plugin

import by.kshakhnitski.onelinestore.user.route.userRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        userRouting()
    }
}
