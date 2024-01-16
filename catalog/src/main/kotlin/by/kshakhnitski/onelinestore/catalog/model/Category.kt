package by.kshakhnitski.onelinestore.catalog.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.select

class Category(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<Category>(Categories) {
        fun existsById(categoryId: Long): Boolean {
            return Categories.slice(Categories.id)
                .select { Categories.id eq categoryId }
                .limit(1)
                .empty().not()
        }
    }

    var name by Categories.name
}

object Categories : LongIdTable("categories") {
    val name = varchar("name", 255)
}
