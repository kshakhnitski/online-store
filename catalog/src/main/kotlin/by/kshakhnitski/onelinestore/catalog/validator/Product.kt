package by.kshakhnitski.onelinestore.catalog.validator

import by.kshakhnitski.onelinestore.catalog.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.catalog.dto.ProductUpdateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import java.math.BigDecimal

class ProductCreateRequestValidator : AbstractValidator<ProductCreateRequest>() {
    override val validation = Validation {
        ProductCreateRequest::name required {
            minLength(3)
            maxLength(255)
        }
        ProductCreateRequest::description required {
            minLength(3)
            maxLength(255)
        }
        ProductCreateRequest::price required {
            minimum(BigDecimal.ZERO)
        }
        ProductCreateRequest::quantity required {
            minimum(0)
        }
        ProductCreateRequest::categoryId required {
            minimum(BigDecimal.ZERO)
        }
    }
}

class ProductUpdateRequestValidator : AbstractValidator<ProductUpdateRequest>() {
    override val validation = Validation {
        ProductUpdateRequest::name ifPresent {
            minLength(3)
            maxLength(255)
        }
        ProductUpdateRequest::description ifPresent {
            minLength(3)
            maxLength(255)
        }
        ProductUpdateRequest::price ifPresent {
            minimum(BigDecimal.ZERO)
        }
        ProductUpdateRequest::quantity ifPresent {
            minimum(0)
        }
        ProductUpdateRequest::categoryId ifPresent {
            minimum(BigDecimal.ZERO)
        }
    }
}