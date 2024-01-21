package by.kshakhnitski.onelinestore.catalog.validator

import by.kshakhnitski.onelinestore.catalog.dto.ValidationErrorItem
import by.kshakhnitski.onelinestore.catalog.exception.ValidationException
import io.konform.validation.Validation
import io.konform.validation.ValidationError

/**
 * Abstract base class for validators.
 *
 * This abstract class provides a common structure for implementing validation logic for different types of data models.
 */
abstract class AbstractValidator<T> : Validator<T> {
    /**
     * Abstract property to be overridden by subclasses with specific validation rules for the respective data type
     */
    protected abstract val validation: Validation<T>

    override fun validate(t: T) {
        validation.validate(t).run {
            if (errors.isNotEmpty()) throw ValidationException(errors.map { it.toValidationErrorItem() })
        }
    }

    private fun ValidationError.toValidationErrorItem(): ValidationErrorItem {
        return ValidationErrorItem(
            field = dataPath.substring(1),
            message = message
        )
    }
}