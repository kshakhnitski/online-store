package by.kshakhnitski.onelinestore.model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

class Product(id: EntityID<Long>) : Entity<Long>(id) {
    companion object : EntityClass<Long, Product>(Products)

    var name by Products.name
    var description by Products.description
    var price by Products.price
    var quantity by Products.quantity
    var categoryId by Products.categoryId
}

object Products : LongIdTable("products") {
    val name = varchar("name", 255)
    val description = varchar("description", 255)
    val price = decimal("price", 10, 2)
    val quantity = integer("quantity")
    val categoryId = long("category_id").references(Categories.id)
}