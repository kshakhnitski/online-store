package by.kshakhnitski.onelinestore.user

import by.kshakhnitski.onelinestore.user.module.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureKoin()
    configureSerialization()
    configureSecurity()
    configureSwagger()
    configureDatabases()
    configureRouting()
    configureStatusPages()
}
