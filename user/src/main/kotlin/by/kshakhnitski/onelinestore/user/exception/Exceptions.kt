package by.kshakhnitski.onelinestore.user.exception

import by.kshakhnitski.onelinestore.user.dto.ValidationErrorItem

class ValidationException(val errors: List<ValidationErrorItem>) : RuntimeException()

class ConflictException(message: String) : RuntimeException(message)

class InvalidCredentialsException(message: String) : RuntimeException(message)
