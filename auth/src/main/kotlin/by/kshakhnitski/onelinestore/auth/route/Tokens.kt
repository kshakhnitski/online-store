package by.kshakhnitski.onelinestore.auth.route

import by.kshakhnitski.onelinestore.auth.dto.GenerateTokensRequest
import by.kshakhnitski.onelinestore.auth.dto.RefreshTokensRequest
import by.kshakhnitski.onelinestore.auth.service.TokenService
import by.kshakhnitski.onelinestore.auth.validator.GenerateTokensRequestValidator
import by.kshakhnitski.onelinestore.auth.validator.RefreshTokensRequestValidator
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.tokensRouting() {
    val tokenService: TokenService by inject()
    val generateTokensRequestValidator: GenerateTokensRequestValidator by inject()
    val refreshTokensRequestValidator: RefreshTokensRequestValidator by inject()

    route("/tokens") {
        post("/generate") {
            val generateTokensRequest = call.receive<GenerateTokensRequest>()
            generateTokensRequestValidator.validate(generateTokensRequest)
            val tokensResponse = tokenService.generateTokens(generateTokensRequest)
            call.respond(tokensResponse)
        }
        post("/refresh") {
            val refreshToken = call.receive<RefreshTokensRequest>()
            refreshTokensRequestValidator.validate(refreshToken)
            val tokensResponse = tokenService.refreshTokens(refreshToken.refreshToken!!)
            call.respond(tokensResponse)
        }
    }
}