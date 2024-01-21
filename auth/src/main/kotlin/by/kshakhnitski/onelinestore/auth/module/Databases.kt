package by.kshakhnitski.onelinestore.auth.module

import by.kshakhnitski.onelinestore.auth.db.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init(environment.config)
}
