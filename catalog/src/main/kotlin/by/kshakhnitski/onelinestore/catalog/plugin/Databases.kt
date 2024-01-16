package by.kshakhnitski.onelinestore.catalog.plugin

import by.kshakhnitski.onelinestore.catalog.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
