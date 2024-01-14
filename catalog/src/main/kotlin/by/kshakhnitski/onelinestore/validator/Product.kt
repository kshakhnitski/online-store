package by.kshakhnitski.onelinestore.validator

import by.kshakhnitski.onelinestore.dto.ProductCreateRequest
import by.kshakhnitski.onelinestore.dto.ProductUpdateRequest
import io.konform.validation.Validation
import io.konform.validation.jsonschema.maxLength
import io.konform.validation.jsonschema.minLength
import io.konform.validation.jsonschema.minimum
import java.math.BigDecimal

val productCreateRequestValidator = Validation {
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

val productUpdateRequestValidator = Validation {
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