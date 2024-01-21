package by.kshakhnitski.onelinestore.user.exception

import io.konform.validation.ValidationErrors

class ValidationException(val errors: ValidationErrors) : RuntimeException()

class ConflictException(message: String) : RuntimeException(message)

class InvalidCredentialsException(message: String) : RuntimeException(message)
