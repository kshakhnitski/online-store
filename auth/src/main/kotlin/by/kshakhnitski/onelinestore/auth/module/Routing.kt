package by.kshakhnitski.onelinestore.auth.module

import by.kshakhnitski.onelinestore.auth.route.tokensRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        tokensRouting()
    }
}
