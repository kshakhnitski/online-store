package by.kshakhnitski.onelinestore.model

import org.jetbrains.exposed.sql.Table

object Products : Table("products") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val categoryId = long("category_id").references(Categories.id)

    override val primaryKey = PrimaryKey(id)
}