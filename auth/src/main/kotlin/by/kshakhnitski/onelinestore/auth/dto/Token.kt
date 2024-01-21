package by.kshakhnitski.onelinestore.auth.dto

import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class GenerateTokensRequest(
    val email: String? = null,
    val password: String? = null,
)

@Serializable
data class RefreshTokensRequest(
    val refreshToken: String? = null,
)

@Serializable
data class TokensResponse(
    val accessToken: String,
    val accessTokenExpiration: Long,
    val refreshToken: String,
    val refreshTokenExpiration: Long,
)

data class TokenDto(
    val value: String,
    val expiredAt: Instant,
)