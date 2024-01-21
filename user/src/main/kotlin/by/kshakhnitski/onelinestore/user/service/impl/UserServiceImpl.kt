package by.kshakhnitski.onelinestore.user.service.impl

import by.kshakhnitski.onelinestore.user.dto.UserCreateRequest
import by.kshakhnitski.onelinestore.user.dto.UserDto
import by.kshakhnitski.onelinestore.user.dto.UserUpdateRequest
import by.kshakhnitski.onelinestore.user.dto.VerifyCredentialsRequest
import by.kshakhnitski.onelinestore.user.exception.ConflictException
import by.kshakhnitski.onelinestore.user.exception.InvalidCredentialsException
import by.kshakhnitski.onelinestore.user.model.User
import by.kshakhnitski.onelinestore.user.service.UserService
import io.ktor.server.plugins.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt
import java.time.Instant

class UserServiceImpl : UserService {

    override suspend fun getAll() = transaction {
        User.all().map { it.toUserDto() }
    }

    override suspend fun getById(id: Long) = transaction {
        User.findById(id)
            ?.toUserDto()
            ?: throw NotFoundException("User [$id] not found")
    }

    override suspend fun create(createRequest: UserCreateRequest) = transaction {
        if (User.existsByEmail(createRequest.email!!)) {
            throw ConflictException("User with email [${createRequest.email}] already exists")
        }
        if (User.existsByPhone(createRequest.phone!!)) {
            throw ConflictException("User with phone [${createRequest.phone}] already exists")
        }

        User.new {
            email = createRequest.email
            phone = createRequest.phone
            firstName = createRequest.firstName!!
            password = BCrypt.hashpw(createRequest.password!!, BCrypt.gensalt())
            registrationDate = Instant.now()
        }.toUserDto()
    }

    override suspend fun verifyCredentials(verifyCredentialsRequest: VerifyCredentialsRequest): UserDto {
        val user = transaction {
            User.findByEmail(verifyCredentialsRequest.email!!)
        } ?: throw NotFoundException("User with email [${verifyCredentialsRequest.email}] not found")

        if (BCrypt.checkpw(verifyCredentialsRequest.password!!, user.password).not()) {
            throw InvalidCredentialsException("Invalid credentials")
        }

        return user.toUserDto()
    }

    override suspend fun update(id: Long, updateRequest: UserUpdateRequest) =
        transaction {
            val user = User.findById(id) ?: throw NotFoundException("User [$id] not found")

            user.apply {
                updateRequest.firstName?.let { this.firstName = it }
            }.toUserDto()
        }

    override suspend fun delete(id: Long) = transaction {
        User.findById(id)
            ?.delete()
            ?: throw NotFoundException("User [$id] not found")
        return@transaction
    }

    private fun User.toUserDto(): UserDto {
        return UserDto(
            id = id.value,
            email = email,
            phone = phone,
            firstName = firstName,
            registrationDate = registrationDate
        )
    }
}