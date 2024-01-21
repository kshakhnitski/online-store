package by.kshakhnitski.onelinestore.auth.exception

import by.kshakhnitski.onelinestore.auth.dto.ApiError
import by.kshakhnitski.onelinestore.auth.dto.ValidationErrorItem

class ValidationException(val errors: List<ValidationErrorItem>) : RuntimeException()

class ConflictException(message: String) : RuntimeException(message)

class ApiClientException(apiError: ApiError) : RuntimeException()

class TokenValidationException(message: String) : RuntimeException(message)