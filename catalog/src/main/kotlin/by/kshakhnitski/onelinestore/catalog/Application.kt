package by.kshakhnitski.onelinestore.catalog

import by.kshakhnitski.onelinestore.catalog.module.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSwagger()
    configureDatabases()
    configureRouting()
    configureStatusPages()
}
