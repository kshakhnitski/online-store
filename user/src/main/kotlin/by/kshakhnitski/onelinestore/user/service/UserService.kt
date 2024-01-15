package by.kshakhnitski.onelinestore.user.service

import by.kshakhnitski.onelinestore.user.dto.UserCreateRequest
import by.kshakhnitski.onelinestore.user.dto.UserDto
import by.kshakhnitski.onelinestore.user.dto.UserUpdateRequest

interface UserService {
    suspend fun getAll(): List<UserDto>
    suspend fun getById(id: Long): UserDto
    suspend fun create(createRequest: UserCreateRequest): UserDto
    suspend fun update(id: Long, updateRequest: UserUpdateRequest): UserDto
    suspend fun delete(id: Long)
}