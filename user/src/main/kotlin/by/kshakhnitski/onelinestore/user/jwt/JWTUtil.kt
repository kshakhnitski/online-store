package by.kshakhnitski.onelinestore.user.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.Payload
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.config.*

object JWTUtil {
    private lateinit var jwtSecret: String
    private lateinit var jwtIssuer: String

    fun init(appConfig: ApplicationConfig) {
        jwtSecret = appConfig.property("jwt.secret").getString()
        jwtIssuer = appConfig.property("jwt.issuer").getString()
    }

    fun createVerifier(): JWTVerifier {
        return JWT
            .require(Algorithm.HMAC256(jwtSecret))
            .withIssuer(jwtIssuer)
            .build()
    }

    fun validateToken(payload: Payload): JWTPrincipal? {
        return if (payload.claims.containsKey("userId")) {
            JWTPrincipal(payload)
        } else null
    }

    fun extractUserId(authentication: AuthenticationContext): Long {
        return authentication.principal<JWTPrincipal>()?.get("userId")!!.toLong()
    }
}