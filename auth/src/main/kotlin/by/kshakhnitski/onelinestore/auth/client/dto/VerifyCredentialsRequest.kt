package by.kshakhnitski.onelinestore.auth.client.dto

import kotlinx.serialization.Serializable

@Serializable
data class VerifyCredentialsRequest(
    val email: String,
    val password: String
)
