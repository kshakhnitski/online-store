package by.kshakhnitski.onelinestore.service.impl

import by.kshakhnitski.onelinestore.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.dto.ProductDto
import by.kshakhnitski.onelinestore.dto.ProductUpdateRequest
import by.kshakhnitski.onelinestore.model.Category
import by.kshakhnitski.onelinestore.model.Product
import by.kshakhnitski.onelinestore.plugin.DatabaseSingleton
import by.kshakhnitski.onelinestore.service.ProductService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductServiceImpl : ProductService {

    override suspend fun getAll() = transaction(DatabaseSingleton.database) {
        Product.all().map { it.toProductDto() }
    }

    override suspend fun getById(id: Long) = transaction(DatabaseSingleton.database) {
        Product.findById(id)
            ?.toProductDto()
            ?: throw NotFoundException("Product [$id] not found")
    }

    override suspend fun create(createRequest: ProductCreateRequest) = transaction(DatabaseSingleton.database) {
        if (Category.findById(createRequest.categoryId!!) == null) {
            throw NotFoundException("Category [${createRequest.categoryId}] not found")
        }

        Product.new {
            name = createRequest.name!!
            description = createRequest.description!!
            price = createRequest.price!!
            quantity = createRequest.quantity!!
            categoryId = createRequest.categoryId
        }.toProductDto()
    }

    override suspend fun update(id: Long, updateRequest: ProductUpdateRequest) =
        transaction(DatabaseSingleton.database) {
            val product = Product.findById(id) ?: throw NotFoundException("Product [$id] not found")

            updateRequest.categoryId?.let {
                if (Category.findById(updateRequest.categoryId) == null) {
                    throw NotFoundException("Category [${updateRequest.categoryId}] not found")
                }
            }

            product.apply {
                name = updateRequest.name ?: product.name
                description = updateRequest.description ?: product.description
                price = updateRequest.price ?: product.price
                quantity = updateRequest.quantity ?: product.quantity
                categoryId = updateRequest.categoryId ?: product.categoryId
            }.toProductDto()
        }

    override suspend fun delete(id: Long) = transaction(DatabaseSingleton.database) {
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
}