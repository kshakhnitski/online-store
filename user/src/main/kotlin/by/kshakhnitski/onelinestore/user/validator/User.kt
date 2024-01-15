package by.kshakhnitski.onelinestore.user.validator

import by.kshakhnitski.onelinestore.user.dto.UserCreateRequest
import by.kshakhnitski.onelinestore.user.dto.UserUpdateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

class UserCreateRequestValidator : AbstractValidator<UserCreateRequest>() {
    override val validation = Validation {
        UserCreateRequest::firstName required {
            minLength(3)
            maxLength(255)
        }
        UserCreateRequest::email required {
            minLength(3)
            maxLength(255)
        }
        UserCreateRequest::password required {
            minLength(3)
            maxLength(255)
        }
        UserCreateRequest::phone required {
            minLength(3)
            maxLength(255)
        }
    }
}

class UserUpdateRequestValidator : AbstractValidator<UserUpdateRequest>() {
    override val validation = Validation {
        UserUpdateRequest::firstName ifPresent {
            minLength(3)
            maxLength(255)
        }
    }
}