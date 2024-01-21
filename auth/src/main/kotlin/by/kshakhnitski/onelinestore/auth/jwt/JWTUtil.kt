package by.kshakhnitski.onelinestore.auth.jwt

import by.kshakhnitski.onelinestore.auth.dto.TokenDto
import io.github.nefilim.kjwt.JWT
import io.github.nefilim.kjwt.sign
import io.ktor.server.config.*
import java.time.Instant
import java.util.*

object JWTUtil {
    private lateinit var secret: String
    private lateinit var issuer: String
    private lateinit var validityInMs: String
    private lateinit var refreshValidityInMs: String

    fun init(appConfig: ApplicationConfig) {
        secret = appConfig.property("jwt.secret").getString()
        issuer = appConfig.property("jwt.issuer").getString()
        validityInMs = appConfig.property("jwt.validityInMs").getString()
        refreshValidityInMs = appConfig.property("jwt.refreshValidityInMs").getString()
    }

    fun generateAccessToken(email: String, userId: String): TokenDto {
        val now = Instant.now()
        val expiredAt = now.plusMillis(validityInMs.toLong())

        val token = JWT.hs256 {
            subject(email)
            issuer(issuer)
            issuedAt(now)
            expiresAt(expiredAt)
            claim("userId", userId)
        }.sign(secret).orNull()
            ?.rendered
            ?: throw RuntimeException("Failed to sign access token")

        return TokenDto(token, expiredAt)
    }

    fun generateRefreshToken() = TokenDto(
        value = UUID.randomUUID().toString(),
        expiredAt = Instant.now().plusMillis(refreshValidityInMs.toLong())
    )
}