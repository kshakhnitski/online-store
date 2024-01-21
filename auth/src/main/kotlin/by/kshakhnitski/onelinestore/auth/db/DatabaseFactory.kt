package by.kshakhnitski.onelinestore.auth.db

import by.kshakhnitski.onelinestore.auth.model.Authorizations
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    private lateinit var url: String
    private lateinit var username: String
    private lateinit var password: String
    private lateinit var driverClassName: String

    fun init(applicationConfig: ApplicationConfig) {
        applicationConfig.run {
            url = property("database.url").getString()
            username = property("database.user").getString()
            password = property("database.password").getString()
            driverClassName = property("database.driver").getString()
        }

        val database = Database.connect(hikari())

        transaction(database) {
            SchemaUtils.create(Authorizations)
        }
    }

    private fun hikari(): HikariDataSource {
        val config = HikariConfig().apply {
            jdbcUrl = url
            driverClassName = DatabaseFactory.driverClassName
            username = DatabaseFactory.username
            password = DatabaseFactory.password
            maximumPoolSize = 10
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
}