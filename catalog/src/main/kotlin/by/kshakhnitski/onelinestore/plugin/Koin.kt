package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.service.CategoryService
import by.kshakhnitski.onelinestore.service.ProductService
import by.kshakhnitski.onelinestore.service.impl.CategoryServiceImpl
import by.kshakhnitski.onelinestore.service.impl.ProductServiceImpl
import by.kshakhnitski.onelinestore.validator.CategoryCreateRequestValidator
import by.kshakhnitski.onelinestore.validator.CategoryUpdateRequestValidator
import by.kshakhnitski.onelinestore.validator.ProductCreateRequestValidator
import by.kshakhnitski.onelinestore.validator.ProductUpdateRequestValidator
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger(level = org.koin.core.logger.Level.DEBUG)

        val serviceModule = module {
            single<CategoryService> { CategoryServiceImpl() }
            single<ProductService> { ProductServiceImpl() }
        }
        val validatorModule = module {
            single<CategoryCreateRequestValidator> { CategoryCreateRequestValidator() }
            single<CategoryUpdateRequestValidator> { CategoryUpdateRequestValidator() }
            single<ProductCreateRequestValidator> { ProductCreateRequestValidator() }
            single<ProductUpdateRequestValidator> { ProductUpdateRequestValidator() }
        }

        modules(listOf(serviceModule, validatorModule))
    }
}