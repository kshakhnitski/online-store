package by.kshakhnitski.onelinestore.auth.service

import by.kshakhnitski.onelinestore.auth.dto.GenerateTokensRequest
import by.kshakhnitski.onelinestore.auth.dto.TokensResponse

interface TokenService {
    suspend fun generateTokens(generateTokensRequest: GenerateTokensRequest): TokensResponse

    suspend fun refreshTokens(refreshToken: String): TokensResponse
}