package by.kshakhnitski.onelinestore

import by.kshakhnitski.onelinestore.plugin.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureHTTP()
    configureDatabases()
    configureRouting()
    configureStatusPages()
}
