package by.kshakhnitski.onelinestore.catalog

import by.kshakhnitski.onelinestore.catalog.plugin.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureHTTP()
    configureDatabases()
    configureRouting()
    configureStatusPages()
}
