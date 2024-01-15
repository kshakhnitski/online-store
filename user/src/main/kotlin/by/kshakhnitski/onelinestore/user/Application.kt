package by.kshakhnitski.onelinestore.user

import by.kshakhnitski.onelinestore.user.plugin.*
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
