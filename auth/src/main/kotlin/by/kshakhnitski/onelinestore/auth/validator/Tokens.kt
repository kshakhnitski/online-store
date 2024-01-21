package by.kshakhnitski.onelinestore.auth.validator

import by.kshakhnitski.onelinestore.auth.dto.GenerateTokensRequest
import by.kshakhnitski.onelinestore.auth.dto.RefreshTokensRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength

class GenerateTokensRequestValidator : AbstractValidator<GenerateTokensRequest>() {
    override val validation: Validation<GenerateTokensRequest>
        get() = Validation {
            GenerateTokensRequest::email required {
                minLength(3)
                maxLength(100)
            }
            GenerateTokensRequest::password required {
                minLength(3)
                maxLength(50)
            }
        }
}

class RefreshTokensRequestValidator : AbstractValidator<RefreshTokensRequest>() {
    override val validation: Validation<RefreshTokensRequest>
        get() = Validation {
            RefreshTokensRequest::refreshToken required {
                minLength(3)
                maxLength(512)
            }
        }
}