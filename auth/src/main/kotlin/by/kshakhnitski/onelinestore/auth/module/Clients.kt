package by.kshakhnitski.onelinestore.auth.module

import by.kshakhnitski.onelinestore.auth.client.ClientConfig
import io.ktor.server.application.*

fun Application.configureClients() {
    ClientConfig.init(environment.config)
}