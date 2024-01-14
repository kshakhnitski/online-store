package by.kshakhnitski.onelinestore.service.impl

import by.kshakhnitski.onelinestore.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.dto.CategoryDto
import by.kshakhnitski.onelinestore.dto.CategoryUpdateRequest
import by.kshakhnitski.onelinestore.model.Categories
import by.kshakhnitski.onelinestore.plugins.DatabaseSingleton
import by.kshakhnitski.onelinestore.service.CategoryService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryServiceImpl : CategoryService {

    override suspend fun getAll() = transaction(DatabaseSingleton.database) {
        Categories.selectAll().map {
            CategoryDto(
                id = it[Categories.id],
                name = it[Categories.name]
            )
        }.toList()
    }

    override suspend fun getById(id: Long) = transaction(DatabaseSingleton.database) {
        Categories.select { Categories.id eq id }.map {
            CategoryDto(
                id = it[Categories.id],
                name = it[Categories.name]
            )
        }.firstOrNull() ?: throw NotFoundException("Category [$id] not found")
    }

    override suspend fun create(createRequest: CategoryCreateRequest) = transaction(DatabaseSingleton.database) {
        val id = Categories.insert {
            it[name] = createRequest.name!!
        } get Categories.id

        CategoryDto(
            id = id,
            name = createRequest.name!!
        )
    }

    override suspend fun update(id: Long, updateRequest: CategoryUpdateRequest) =
        transaction(DatabaseSingleton.database) {
            Categories.update({ Categories.id eq id }) { entity ->
                updateRequest.name?.let {
                    entity[name] = it
                }
            }
            Categories.select { Categories.id eq id }.map {
                CategoryDto(
                    id = it[Categories.id],
                    name = it[Categories.name]
                )
            }.firstOrNull() ?: throw NotFoundException("Category [$id] not found")
        }

    override suspend fun delete(id: Long) = transaction(DatabaseSingleton.database) {
        Categories.deleteWhere { Categories.id eq id } > 0 ||
                throw NotFoundException("Category [$id] not found")
        return@transaction
    }
}