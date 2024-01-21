package by.kshakhnitski.onelinestore.auth

import by.kshakhnitski.onelinestore.auth.module.*
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
    configureClients()
    configureJWT()
}
