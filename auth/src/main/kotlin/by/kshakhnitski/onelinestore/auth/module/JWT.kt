package by.kshakhnitski.onelinestore.auth.module

import by.kshakhnitski.onelinestore.auth.jwt.JWTUtil
import io.ktor.server.application.*

fun Application.configureJWT() {
    JWTUtil.init(environment.config)
}