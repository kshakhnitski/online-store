package by.kshakhnitski.onelinestore.catalog.exception

import by.kshakhnitski.onelinestore.catalog.dto.ValidationErrorItem

class ValidationException(val errors: List<ValidationErrorItem>) : RuntimeException()
