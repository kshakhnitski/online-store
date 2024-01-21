package by.kshakhnitski.onelinestore.auth.module

import by.kshakhnitski.onelinestore.auth.client.UserClient
import by.kshakhnitski.onelinestore.auth.client.impl.UserClientImpl
import by.kshakhnitski.onelinestore.auth.service.TokenService
import by.kshakhnitski.onelinestore.auth.service.impl.TokensServiceImpl
import by.kshakhnitski.onelinestore.auth.validator.GenerateTokensRequestValidator
import by.kshakhnitski.onelinestore.auth.validator.RefreshTokensRequestValidator
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = org.koin.core.logger.Level.DEBUG)

        val clientModule = module {
            single<UserClient> { UserClientImpl() }
        }
        val validatorModule = module {
            single<GenerateTokensRequestValidator> { GenerateTokensRequestValidator() }
            single<RefreshTokensRequestValidator> { RefreshTokensRequestValidator() }
        }
        val serviceModule = module {
            single<TokenService> { TokensServiceImpl(get()) }
        }

        modules(
            listOf(
                clientModule,
                validatorModule,
                serviceModule,
            )
        )
    }
}