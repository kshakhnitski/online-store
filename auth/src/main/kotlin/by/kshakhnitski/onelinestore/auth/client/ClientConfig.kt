package by.kshakhnitski.onelinestore.auth.client

import io.ktor.server.config.*

object ClientConfig {
    lateinit var userUrl: String

    fun init(appConfig: ApplicationConfig) {
        userUrl = appConfig.property("client.userUrl").getString()
    }
}