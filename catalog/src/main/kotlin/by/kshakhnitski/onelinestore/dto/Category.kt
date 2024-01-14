package by.kshakhnitski.onelinestore.dto

import kotlinx.serialization.Serializable

@Serializable
data class CategoryDto(
    var id: Long = 0,
    val name: String
)

@Serializable
data class CategoryCreateRequest(
    val name: String? = null
)

@Serializable
data class CategoryUpdateRequest(
    val name: String? = null
)