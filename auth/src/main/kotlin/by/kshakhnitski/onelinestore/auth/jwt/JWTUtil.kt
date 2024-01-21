package by.kshakhnitski.onelinestore.auth.jwt

import by.kshakhnitski.onelinestore.auth.dto.TokenDto
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.config.*
import java.time.Instant
import java.util.*

object JWTUtil {
    private lateinit var jwtSecret: String
    private lateinit var jwtIssuer: String
    private lateinit var jwtAccessValidityInMs: String
    private lateinit var jwtRefreshValidityInMs: String

    private val algorithm: Algorithm
        get() = Algorithm.HMAC256(jwtSecret)

    fun init(appConfig: ApplicationConfig) {
        jwtSecret = appConfig.property("jwt.secret").getString()
        jwtIssuer = appConfig.property("jwt.issuer").getString()
        jwtAccessValidityInMs = appConfig.property("jwt.accessValidityInMs").getString()
        jwtRefreshValidityInMs = appConfig.property("jwt.refreshValidityInMs").getString()
    }

    fun generateAccessToken(email: String, userId: String): TokenDto {
        val now = Instant.now()
        val expiredAt = now.plusMillis(jwtAccessValidityInMs.toLong())

        val token = JWT.create()
            .withSubject(email)
            .withIssuer(jwtIssuer)
            .withIssuedAt(Date.from(now))
            .withExpiresAt(Date.from(expiredAt))
            .withClaim("userId", userId)
            .sign(algorithm)

        return TokenDto(token, expiredAt)
    }

    fun generateRefreshToken() = TokenDto(
        value = UUID.randomUUID().toString(),
        expiredAt = Instant.now().plusMillis(jwtRefreshValidityInMs.toLong())
    )
}