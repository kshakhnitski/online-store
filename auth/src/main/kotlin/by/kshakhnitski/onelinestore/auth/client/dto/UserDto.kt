package by.kshakhnitski.onelinestore.auth.client.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: Long,
    val email: String
)
