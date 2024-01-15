package by.kshakhnitski.onelinestore.service.catalog

import by.kshakhnitski.onelinestore.catalog.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.CategoryUpdateRequest
import by.kshakhnitski.onelinestore.catalog.model.Categories
import by.kshakhnitski.onelinestore.catalog.model.Category
import by.kshakhnitski.onelinestore.catalog.service.impl.CategoryServiceImpl
import io.ktor.server.plugins.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class CategoryServiceTest {

    private val db by lazy {
        Database.connect("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;", driver = "org.h2.Driver")
    }
    private val categoryService by lazy {
        CategoryServiceImpl()
    }

    @BeforeEach
    fun setup() = transaction(db) {
        SchemaUtils.drop(Categories)
        SchemaUtils.create(Categories)
    }

    @Test
    fun `getById category returns category`() = runBlocking {
        val category = transaction(db) {
            Category.new {
                name = "test"
            }
        }

        val result = categoryService.getById(category.id.value)

        assertNotNull(result)
        assertEquals(category.id.value, result.id)
        assertEquals(category.name, result.name)
    }

    @Test
    fun `getAll categories returns categories`() = runBlocking {
        val category1 = transaction(db) {
            Category.new {
                name = "test1"
            }
        }
        val category2 = transaction(db) {
            Category.new {
                name = "test2"
            }
        }

        val result = categoryService.getAll()

        assertEquals(2, result.size)

        assertEquals(category1.id.value, result[0].id)
        assertEquals(category1.name, result[0].name)

        assertEquals(category2.id.value, result[1].id)
        assertEquals(category2.name, result[1].name)
    }

    @Test
    fun `create category returns category`() = runBlocking {
        val categoryCreateRequest = CategoryCreateRequest(
            name = "test"
        )

        val result = categoryService.create(categoryCreateRequest)

        assertNotNull(result)
        assertEquals(categoryCreateRequest.name, result.name)
    }

    @Test
    fun `update category returns category`() = runBlocking {
        val category = transaction(db) {
            Category.new {
                name = "test"
            }
        }
        val categoryUpdateRequest = CategoryUpdateRequest(
            name = "test2"
        )

        val result = categoryService.update(category.id.value, categoryUpdateRequest)

        assertNotNull(result)
        assertEquals(category.id.value, result.id)
        assertEquals(categoryUpdateRequest.name, result.name)
    }

    @Test
    fun `delete category makes category not found`() = runBlocking {
        val category = transaction(db) {
            Category.new {
                name = "test"
            }
        }

        categoryService.delete(category.id.value)

        val foundCategory = transaction(db) {
            Category.findById(category.id.value)
        }

        assertNull(foundCategory)
    }

    @Test
    fun `get category throws NotFoundException after delete category`() = runBlocking {
        val category = transaction(db) {
            Category.new {
                name = "test"
            }
        }

        categoryService.delete(category.id.value)

        assertThrows<NotFoundException> {
            categoryService.getById(category.id.value)
        }
        return@runBlocking
    }
}