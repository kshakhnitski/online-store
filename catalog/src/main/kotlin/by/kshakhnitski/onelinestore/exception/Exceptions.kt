package by.kshakhnitski.onelinestore.exception

import io.konform.validation.ValidationErrors

class ValidationException(val errors: ValidationErrors) : RuntimeException()
