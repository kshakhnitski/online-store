package by.kshakhnitski.onelinestore.catalog.dto

import by.kshakhnitski.onelinestore.catalog.plugin.BigDecimalSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class ProductDto(
    val id: Long = 0,
    val name: String,
    val description: String,
    @Serializable(with = BigDecimalSerializer::class)
    val price: BigDecimal,
    val quantity: Int,
    val categoryId: Long,
)

@Serializable
data class ProductCreateRequest(
    val name: String? = null,
    val description: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    val price: BigDecimal? = null,
    val quantity: Int? = null,
    val categoryId: Long? = null
)

@Serializable
data class ProductUpdateRequest(
    val name: String? = null,
    val description: String? = null,
    @Serializable(with = BigDecimalSerializer::class)
    val price: BigDecimal? = null,
    val quantity: Int? = null,
    val categoryId: Long? = null
)
