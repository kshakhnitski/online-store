package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
