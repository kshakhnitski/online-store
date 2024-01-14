package by.kshakhnitski.onelinestore.validator

import by.kshakhnitski.onelinestore.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.dto.CategoryUpdateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

val categoryCreateRequestValidator = Validation {
    CategoryCreateRequest::name required {
        minLength(3)
        maxLength(255)
    }
}

val categoryUpdateRequestValidator = Validation {
    CategoryUpdateRequest::name ifPresent {
        minLength(3)
        maxLength(255)
    }
}