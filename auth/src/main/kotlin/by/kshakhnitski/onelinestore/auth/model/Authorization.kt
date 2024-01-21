package by.kshakhnitski.onelinestore.auth.model

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.timestamp
import java.util.*

class Authorization(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Authorization>(Authorizations) {
        fun findByRefreshToken(refreshToken: String) =
            find { Authorizations.refreshToken eq refreshToken }.firstOrNull()

        fun findByUserId(userId: String) =
            find { Authorizations.userId eq userId }.firstOrNull()
    }

    var userId by Authorizations.userId
    var refreshToken by Authorizations.refreshToken
    var expiredAt by Authorizations.expiredAt
}

object Authorizations : UUIDTable("authorizations") {
    val userId = varchar("user_id", 255)
    val refreshToken = varchar("refreshToken", 255)
    val expiredAt = timestamp("expired_at")
}