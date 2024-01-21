package by.kshakhnitski.onelinestore.user.route

import by.kshakhnitski.onelinestore.user.dto.UserCreateRequest
import by.kshakhnitski.onelinestore.user.dto.UserUpdateRequest
import by.kshakhnitski.onelinestore.user.dto.VerifyCredentialsRequest
import by.kshakhnitski.onelinestore.user.service.UserService
import by.kshakhnitski.onelinestore.user.validator.UserCreateRequestValidator
import by.kshakhnitski.onelinestore.user.validator.UserUpdateRequestValidator
import by.kshakhnitski.onelinestore.user.validator.VerifyCredentialsRequestValidator
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Routing.userRouting() {
    val userService: UserService by inject()
    val userCreateRequestValidator: UserCreateRequestValidator by inject()
    val userUpdateRequestValidator: UserUpdateRequestValidator by inject()
    val verifyCredentialsRequestValidator: VerifyCredentialsRequestValidator by inject()

    route("/users") {
        get {
            call.respond(userService.getAll())
        }
        post {
            val createRequest = call.receive<UserCreateRequest>()
            userCreateRequestValidator.validate(createRequest)
            call.respond(HttpStatusCode.Created, userService.create(createRequest))
        }
        post("verify-credentials") {
            val verifyCredentialsRequest = call.receive<VerifyCredentialsRequest>()
            verifyCredentialsRequestValidator.validate(verifyCredentialsRequest)
            val user = userService.verifyCredentials(verifyCredentialsRequest)
            call.respond(user)
        }
        get("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("User id [${call.parameters["id"]}] is not valid")
            call.respond(userService.getById(id))
        }
        patch("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("User id [${call.parameters["id"]}] is not valid")
            val updateRequest = call.receive<UserUpdateRequest>()
            userUpdateRequestValidator.validate(updateRequest)
            call.respond(userService.update(id, updateRequest))
        }
        delete("{id}") {
            val id = call.parameters["id"]?.toLongOrNull()
                ?: throw BadRequestException("User id [${call.parameters["id"]}] is not valid")
            userService.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
    }
}