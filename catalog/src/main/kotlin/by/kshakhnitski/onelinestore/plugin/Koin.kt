package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.service.CategoryService
import by.kshakhnitski.onelinestore.service.ProductService
import by.kshakhnitski.onelinestore.service.impl.CategoryServiceImpl
import by.kshakhnitski.onelinestore.service.impl.ProductServiceImpl
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()

        val serviceModule = module {
            single<CategoryService> { CategoryServiceImpl() }
            single<ProductService> { ProductServiceImpl() }
        }

        modules(serviceModule)
    }
}