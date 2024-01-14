package by.kshakhnitski.onelinestore.plugin

import by.kshakhnitski.onelinestore.model.Categories
import by.kshakhnitski.onelinestore.model.Products
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseSingleton {
    lateinit var database: Database
}

fun Application.configureDatabases() {
    val database = Database.connect(
        url = environment.config.property("database.url").getString(),
        driver = environment.config.property("database.driver").getString(),
        user = environment.config.property("database.user").getString(),
        password = environment.config.property("database.password").getString()
    )
    DatabaseSingleton.database = database

    transaction(database) {
        SchemaUtils.create(Categories, Products)
    }
}
