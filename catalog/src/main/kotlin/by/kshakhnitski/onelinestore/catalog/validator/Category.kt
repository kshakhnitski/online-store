package by.kshakhnitski.onelinestore.catalog.validator

import by.kshakhnitski.onelinestore.catalog.dto.CategoryCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.CategoryUpdateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

class CategoryCreateRequestValidator : AbstractValidator<CategoryCreateRequest>() {
    override val validation = Validation {
        CategoryCreateRequest::name required {
            minLength(3)
            maxLength(255)
        }
    }
}

class CategoryUpdateRequestValidator : AbstractValidator<CategoryUpdateRequest>() {
    override val validation = Validation {
        CategoryUpdateRequest::name ifPresent {
            minLength(3)
            maxLength(255)
        }
    }
}
