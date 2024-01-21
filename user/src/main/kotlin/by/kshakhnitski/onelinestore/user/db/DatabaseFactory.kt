package by.kshakhnitski.onelinestore.user.db

import by.kshakhnitski.onelinestore.user.model.Users
import com.typesafe.config.ConfigFactory
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

    fun init() {
        val appConfig = HoconApplicationConfig(ConfigFactory.load())
        url = appConfig.property("database.url").getString()
        username = appConfig.property("database.user").getString()
        password = appConfig.property("database.password").getString()
        driverClassName = appConfig.property("database.driver").getString()

        Database.connect(hikari())

        transaction {
            SchemaUtils.create(Users)
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