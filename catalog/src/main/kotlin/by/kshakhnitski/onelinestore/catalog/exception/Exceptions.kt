package by.kshakhnitski.onelinestore.catalog.exception

import io.konform.validation.ValidationErrors

class ValidationException(val errors: ValidationErrors) : RuntimeException()
