package by.kshakhnitski.onelinestore.catalog.module

import by.kshakhnitski.onelinestore.catalog.db.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
