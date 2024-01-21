package by.kshakhnitski.onelinestore.user.module

import by.kshakhnitski.onelinestore.user.exception.JwtValidationException
import by.kshakhnitski.onelinestore.user.jwt.JWTUtil
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*

fun Application.configureSecurity() {
    JWTUtil.init(environment.config)
    authentication {
        jwt("auth-jwt") {
            verifier(JWTUtil.createVerifier())
            validate { credential ->
                JWTUtil.validateToken(credential.payload)
            }
            challenge { _, _ ->
                val message = call.authentication.allErrors.firstOrNull()
                    ?.message
                    ?: "Token is not valid or has expired"
                throw JwtValidationException(message)
            }
        }
    }
}