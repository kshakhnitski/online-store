package by.kshakhnitski.onelinestore.service

import by.kshakhnitski.onelinestore.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.dto.CategoryDto
import by.kshakhnitski.onelinestore.dto.CategoryUpdateRequest

interface CategoryService {
    suspend fun getAll(): List<CategoryDto>
    suspend fun getById(id: Long): CategoryDto
    suspend fun create(createRequest: CategoryCreateRequest): CategoryDto
    suspend fun update(id: Long, updateRequest: CategoryUpdateRequest): CategoryDto
    suspend fun delete(id: Long)
}