package by.kshakhnitski.onelinestore.service.impl

import by.kshakhnitski.onelinestore.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.dto.ProductDto
import by.kshakhnitski.onelinestore.dto.ProductUpdateRequest
import by.kshakhnitski.onelinestore.model.Category
import by.kshakhnitski.onelinestore.model.Product
import by.kshakhnitski.onelinestore.model.Products
import by.kshakhnitski.onelinestore.service.ProductService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction

class ProductServiceImpl : ProductService {

    override suspend fun getAll(
        categoryId: Long?,
    ) = transaction {
        Product.find {
            Op.build {
                val conditions = mutableListOf<Op<Boolean>>()

                categoryId?.let {
                    checkIfCategoryExists(categoryId)
                    conditions.add(Products.categoryId eq categoryId)
                }

                conditions.reduceOrNull { acc, op -> acc and op } ?: Op.TRUE
            }
        }.map { it.toProductDto() }
    }

    override suspend fun getById(id: Long) = transaction {
        Product.findById(id)
            ?.toProductDto()
            ?: throw NotFoundException("Product [$id] not found")
    }

    override suspend fun create(createRequest: ProductCreateRequest) = transaction {
        checkIfCategoryExists(createRequest.categoryId!!)

        Product.new {
            name = createRequest.name!!
            description = createRequest.description!!
            price = createRequest.price!!
            quantity = createRequest.quantity!!
            categoryId = createRequest.categoryId
        }.toProductDto()
    }

    override suspend fun update(id: Long, updateRequest: ProductUpdateRequest) =
        transaction {
            val product = Product.findById(id) ?: throw NotFoundException("Product [$id] not found")

            updateRequest.categoryId?.let { checkIfCategoryExists(it) }

            product.apply {
                name = updateRequest.name ?: product.name
                description = updateRequest.description ?: product.description
                price = updateRequest.price ?: product.price
                quantity = updateRequest.quantity ?: product.quantity
                categoryId = updateRequest.categoryId ?: product.categoryId
            }.toProductDto()
        }

    override suspend fun delete(id: Long) = transaction {
        Product.findById(id)
            ?.delete()
            ?: throw NotFoundException("Product [$id] not found")
        return@transaction
    }

    private fun Product.toProductDto(): ProductDto {
        return ProductDto(
            id = id.value,
            name = name,
            description = description,
            price = price,
            quantity = quantity,
            categoryId = categoryId
        )
    }

    private fun checkIfCategoryExists(id: Long) {
        if (Category.existsById(id).not()) {
            throw NotFoundException("Category [$id] not found")
        }
    }
}