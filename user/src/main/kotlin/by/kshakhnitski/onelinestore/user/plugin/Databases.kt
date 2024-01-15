package by.kshakhnitski.onelinestore.user.plugin

import by.kshakhnitski.onelinestore.user.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
