package by.kshakhnitski.onelinestore.model

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable

class Category(id: EntityID<Long>) : Entity<Long>(id) {
    companion object : EntityClass<Long, Category>(Categories)

    var name by Categories.name
}

object Categories : LongIdTable("categories") {
    val name = varchar("name", 255)
}
