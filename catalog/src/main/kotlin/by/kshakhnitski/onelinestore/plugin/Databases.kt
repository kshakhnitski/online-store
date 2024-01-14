package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.model.Categories
import by.kshakhnitski.onelinestore.model.Products
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    val config = HikariConfig().apply {
        jdbcUrl = environment.config.property("database.url").getString()
        driverClassName = environment.config.property("database.driver").getString()
        username = environment.config.property("database.user").getString()
        password = environment.config.property("database.password").getString()
        maximumPoolSize = 10
        isAutoCommit = false
        transactionIsolation = "TRANSACTION_REPEATABLE_READ"
        validate()
    }
    val datasource = HikariDataSource(config)
    Database.connect(datasource)

    transaction {
        SchemaUtils.create(Categories, Products)
    }
}
