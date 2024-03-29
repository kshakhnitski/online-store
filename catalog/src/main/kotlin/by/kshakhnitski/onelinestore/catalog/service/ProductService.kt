package by.kshakhnitski.onelinestore.catalog.service

import by.kshakhnitski.onelinestore.catalog.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.ProductDto
import by.kshakhnitski.onelinestore.catalog.dto.ProductUpdateRequest

interface ProductService {
    suspend fun getAll(
        categoryId: Long? = null,
    ): List<ProductDto>

    suspend fun getById(id: Long): ProductDto
    suspend fun create(createRequest: ProductCreateRequest): ProductDto
    suspend fun update(id: Long, updateRequest: ProductUpdateRequest): ProductDto
    suspend fun delete(id: Long)
}