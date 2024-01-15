package by.kshakhnitski.onelinestore.user.plugin

import by.kshakhnitski.onelinestore.user.service.UserService
import by.kshakhnitski.onelinestore.user.service.impl.UserServiceImpl
import by.kshakhnitski.onelinestore.user.validator.UserCreateRequestValidator
import by.kshakhnitski.onelinestore.user.validator.UserUpdateRequestValidator
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = org.koin.core.logger.Level.DEBUG)

        val serviceModule = module {
            single<UserService> { UserServiceImpl() }
        }
        val validatorModule = module {
            single<UserCreateRequestValidator> { UserCreateRequestValidator() }
            single<UserUpdateRequestValidator> { UserUpdateRequestValidator() }
        }

        modules(listOf(serviceModule, validatorModule))
    }
}