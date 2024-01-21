package by.kshakhnitski.onelinestore.auth.client

import by.kshakhnitski.onelinestore.auth.client.dto.UserDto
import by.kshakhnitski.onelinestore.auth.client.dto.VerifyCredentialsRequest

interface UserClient {

    suspend fun verifyCredentials(verifyCredentialsRequest: VerifyCredentialsRequest): UserDto

    suspend fun getUserById(id: String): UserDto
}