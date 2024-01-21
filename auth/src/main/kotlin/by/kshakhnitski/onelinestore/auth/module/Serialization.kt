package by.kshakhnitski.onelinestore.auth.module

import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.serialization.json.Json

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json(contentType = ContentType.Application.Json, json = Json {
            prettyPrint = true
            ignoreUnknownKeys = true
        })
    }
}
