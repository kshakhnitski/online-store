package by.kshakhnitski.onelinestore.catalog.service.impl

import by.kshakhnitski.onelinestore.catalog.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.CategoryDto
import by.kshakhnitski.onelinestore.catalog.dto.CategoryUpdateRequest
import by.kshakhnitski.onelinestore.catalog.model.Category
import by.kshakhnitski.onelinestore.catalog.service.CategoryService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryServiceImpl : CategoryService {

    override suspend fun getAll() = transaction {
        Category.all().map { it.toCategoryDto() }
    }

    override suspend fun getById(id: Long) = transaction {
        Category.findById(id)?.toCategoryDto() ?: throw NotFoundException("Category [$id] not found")
    }

    override suspend fun create(createRequest: CategoryCreateRequest) = transaction {
        Category.new {
            name = createRequest.name!!
        }.toCategoryDto()
    }

    override suspend fun update(id: Long, updateRequest: CategoryUpdateRequest) =
        transaction {
            val category = Category.findById(id)?.apply {
                updateRequest.name?.let { name = it }
            } ?: throw NotFoundException("Category [$id] not found")
            category.toCategoryDto()
        }

    override suspend fun delete(id: Long) = transaction {
        Category.findById(id)
            ?.delete()
            ?: throw NotFoundException("Category [$id] not found")
        return@transaction
    }

    private fun Category.toCategoryDto(): CategoryDto {
        return CategoryDto(
            id = id.value,
            name = name
        )
    }
}