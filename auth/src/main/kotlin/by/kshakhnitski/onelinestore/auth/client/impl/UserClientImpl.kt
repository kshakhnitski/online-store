package by.kshakhnitski.onelinestore.auth.client.impl

import by.kshakhnitski.onelinestore.auth.client.ClientConfig
import by.kshakhnitski.onelinestore.auth.client.UserClient
import by.kshakhnitski.onelinestore.auth.client.dto.UserDto
import by.kshakhnitski.onelinestore.auth.client.dto.VerifyCredentialsRequest
import by.kshakhnitski.onelinestore.auth.dto.ApiError
import by.kshakhnitski.onelinestore.auth.exception.ApiClientException
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class UserClientImpl : UserClient {
    private val client: HttpClient
        get() = HttpClient {
//            install(Logging) {
//                logger = Logger.DEFAULT
//                level = LogLevel.ALL
//            }
            install(ContentNegotiation) {
                json(contentType = ContentType.Application.Json, json = Json {
                    ignoreUnknownKeys = true
                })
            }
            defaultRequest {
                url(ClientConfig.userUrl)
            }
            expectSuccess = true
            HttpResponseValidator {
                handleResponseExceptionWithRequest { exception, _ ->
                    val clientException = exception as? ClientRequestException
                        ?: return@handleResponseExceptionWithRequest

                    val response = clientException.response

                    if (response.status.isSuccess().not()) {
                        val apiError = response.body<ApiError>()
                        throw ApiClientException(apiError = apiError)
                    }
                }
            }
        }

    override suspend fun verifyCredentials(verifyCredentialsRequest: VerifyCredentialsRequest) = client.use { client ->
        client
            .post("/users/verify-credentials") {
                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json)
                    append(HttpHeaders.Accept, ContentType.Application.Json)
                }
                setBody(verifyCredentialsRequest)
            }
            .body<UserDto>()
    }

    override suspend fun getUserById(id: String) = client.use { client ->
        client
            .get("/users/$id") {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json)
                }
            }
            .body<UserDto>()
    }
}