package by.kshakhnitski.onelinestore.user.model

import org.jetbrains.exposed.dao.LongEntity
import org.jetbrains.exposed.dao.LongEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.javatime.timestamp
import org.jetbrains.exposed.sql.select

class User(id: EntityID<Long>) : LongEntity(id) {
    companion object : LongEntityClass<User>(Users) {

        fun existsByEmail(email: String): Boolean {
            return Users.slice(Users.email)
                .select { Users.email eq email }
                .limit(1)
                .empty().not()
        }

        fun existsByPhone(phone: String): Boolean {
            return Users.slice(Users.phone)
                .select { Users.phone eq phone }
                .limit(1)
                .empty().not()
        }
    }

    var email by Users.email
    var password by Users.password
    var firstName by Users.firstName
    var phone by Users.phone
    var registrationDate by Users.registrationDate
}

object Users : LongIdTable("users") {
    val email = varchar("email", 255).uniqueIndex()
    val password = varchar("password", 255)
    val firstName = varchar("first_name", 255)
    val phone = varchar("phone", 255).uniqueIndex()
    val registrationDate = timestamp("registration_date")
}