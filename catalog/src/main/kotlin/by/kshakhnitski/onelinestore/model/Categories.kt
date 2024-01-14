package by.kshakhnitski.onelinestore.model

import org.jetbrains.exposed.sql.Table

object Categories : Table("categories") {
    val id = long("id").autoIncrement()
    val name = varchar("name", 255)

    override val primaryKey = PrimaryKey(id)
}