@file:UseSerializers(InstantSerializer::class)

package by.kshakhnitski.onelinestore.user.dto

import by.kshakhnitski.onelinestore.user.module.InstantSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import java.time.Instant

@Serializable
data class UserDto(
    val id: Long,
    val email: String,
    val firstName: String,
    val phone: String,
    val registrationDate: Instant,
)

@Serializable
data class UserCreateRequest(
    val email: String? = null,
    val firstName: String? = null,
    val phone: String? = null,
    val password: String? = null,
)

@Serializable
data class UserUpdateRequest(
    val firstName: String? = null,
)

@Serializable
data class VerifyCredentialsRequest(
    val email: String? = null,
    val password: String? = null
)
