package by.kshakhnitski.onelinestore.catalog.dto

import kotlinx.serialization.Serializable

@Serializable
data class ApiError(
    val code: Int,
    val status: String,
    val message: String,
)

@Serializable
data class ValidationApiError(
    val code: Int,
    val status: String,
    val message: String,
    val validationErrors: List<ValidationErrorItem>
)

@Serializable
data class ValidationErrorItem(
    val field: String,
    val message: String
)