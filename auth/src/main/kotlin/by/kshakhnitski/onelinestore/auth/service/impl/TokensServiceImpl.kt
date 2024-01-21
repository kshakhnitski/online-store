package by.kshakhnitski.onelinestore.auth.service.impl

import by.kshakhnitski.onelinestore.auth.client.UserClient
import by.kshakhnitski.onelinestore.auth.client.dto.VerifyCredentialsRequest
import by.kshakhnitski.onelinestore.auth.dto.GenerateTokensRequest
import by.kshakhnitski.onelinestore.auth.dto.TokensResponse
import by.kshakhnitski.onelinestore.auth.exception.TokenValidationException
import by.kshakhnitski.onelinestore.auth.jwt.JWTUtil
import by.kshakhnitski.onelinestore.auth.model.Authorization
import by.kshakhnitski.onelinestore.auth.service.TokenService
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant

class TokensServiceImpl(
    private val userClient: UserClient
) : TokenService {

    override suspend fun generateTokens(generateTokensRequest: GenerateTokensRequest): TokensResponse {
        val user = userClient.verifyCredentials(
            VerifyCredentialsRequest(
                email = generateTokensRequest.email!!,
                password = generateTokensRequest.password!!
            )
        )

        val userId = user.id.toString()

        val accessToken = JWTUtil.generateAccessToken(email = user.email, userId = userId)
        val refreshToken = JWTUtil.generateRefreshToken()

        transaction {
            Authorization.findByUserId(userId)?.apply {
                this.refreshToken = refreshToken.value
                this.expiredAt = refreshToken.expiredAt
            } ?: Authorization.new {
                this.userId = userId
                this.refreshToken = refreshToken.value
                this.expiredAt = refreshToken.expiredAt
            }
        }

        return TokensResponse(
            accessToken = accessToken.value,
            accessTokenExpiration = accessToken.expiredAt.toEpochMilli(),
            refreshToken = refreshToken.value,
            refreshTokenExpiration = refreshToken.expiredAt.toEpochMilli()
        )
    }

    override suspend fun refreshTokens(refreshToken: String) = transaction {
        val authorization = Authorization.findByRefreshToken(refreshToken)
            ?: throw TokenValidationException("Refresh token is invalid")

        val user = runBlocking { userClient.getUserById(authorization.userId) }

        if (authorization.expiredAt.isBefore(Instant.now())) {
            throw TokenValidationException("Refresh token is expired")
        }

        val userId = user.id.toString()
        val newAccessToken = JWTUtil.generateAccessToken(email = user.email, userId = userId)
        val newRefreshToken = JWTUtil.generateRefreshToken()

        authorization.refreshToken = newRefreshToken.value
        authorization.expiredAt = newRefreshToken.expiredAt

        TokensResponse(
            accessToken = newAccessToken.value,
            accessTokenExpiration = newAccessToken.expiredAt.toEpochMilli(),
            refreshToken = newRefreshToken.value,
            refreshTokenExpiration = newRefreshToken.expiredAt.toEpochMilli()
        )
    }
}