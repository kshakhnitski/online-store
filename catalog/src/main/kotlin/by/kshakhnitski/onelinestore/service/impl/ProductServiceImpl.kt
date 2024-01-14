package by.kshakhnitski.onelinestore.service.impl

import by.kshakhnitski.onelinestore.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.dto.ProductDto
import by.kshakhnitski.onelinestore.dto.ProductUpdateRequest
import by.kshakhnitski.onelinestore.model.Categories
import by.kshakhnitski.onelinestore.model.Products
import by.kshakhnitski.onelinestore.plugins.DatabaseSingleton
import by.kshakhnitski.onelinestore.service.ProductService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class ProductServiceImpl : ProductService {

    override suspend fun getAll() = transaction(DatabaseSingleton.database) {
        Products.selectAll().map {
            ProductDto(
                id = it[Products.id],
                name = it[Products.name],
                description = it[Products.description],
                price = it[Products.price],
                quantity = it[Products.quantity],
                categoryId = it[Products.categoryId]
            )
        }.toList()
    }

    override suspend fun getById(id: Long) = transaction(DatabaseSingleton.database) {
        Products.select { Products.id eq id }.map {
            ProductDto(
                id = it[Products.id],
                name = it[Products.name],
                description = it[Products.description],
                price = it[Products.price],
                quantity = it[Products.quantity],
                categoryId = it[Products.categoryId]
            )
        }.firstOrNull() ?: throw NotFoundException("Product [$id] not found")
    }

    override suspend fun create(createRequest: ProductCreateRequest) = transaction(DatabaseSingleton.database) {
        if (Categories.select { Categories.id eq createRequest.categoryId!! }.any().not()) {
            throw NotFoundException("Category [${createRequest.categoryId}] not found")
        }

        val id = Products.insert {
            it[name] = createRequest.name!!
            it[description] = createRequest.description!!
            it[price] = createRequest.price!!
            it[quantity] = createRequest.quantity!!
            it[categoryId] = createRequest.categoryId!!
        } get Products.id

        ProductDto(
            id = id,
            name = createRequest.name!!,
            description = createRequest.description!!,
            price = createRequest.price!!,
            quantity = createRequest.quantity!!,
            categoryId = createRequest.categoryId!!
        )
    }

    override suspend fun update(id: Long, updateRequest: ProductUpdateRequest) =
        transaction(DatabaseSingleton.database) {
            Products.update({ Products.id eq id }) { entity ->
                updateRequest.name?.let {
                    entity[name] = it
                }
                updateRequest.description?.let {
                    entity[description] = it
                }
                updateRequest.price?.let {
                    entity[price] = it
                }
                updateRequest.categoryId?.let {
                    if (Categories.select { Categories.id eq it }.any().not()) {
                        throw NotFoundException("Category [$it] not found")
                    }

                    entity[categoryId] = it
                }
            }
            Products.select { Products.id eq id }.map {
                ProductDto(
                    id = it[Products.id],
                    name = it[Products.name],
                    description = it[Products.description],
                    price = it[Products.price],
                    quantity = it[Products.quantity],
                    categoryId = it[Products.categoryId]
                )
            }.firstOrNull() ?: throw NotFoundException("Product [$id] not found")
        }

    override suspend fun delete(id: Long) = transaction(DatabaseSingleton.database) {
        Products.deleteWhere { Products.id eq id } > 0 ||
                throw NotFoundException("Product [$id] not found")
        return@transaction
    }
}