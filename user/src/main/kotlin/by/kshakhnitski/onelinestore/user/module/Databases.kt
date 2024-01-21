package by.kshakhnitski.onelinestore.user.module

import by.kshakhnitski.onelinestore.user.db.DatabaseFactory
import io.ktor.server.application.*

fun Application.configureDatabases() {
    DatabaseFactory.init()
}
