package by.kshakhnitski.onelinestore.validator

import by.kshakhnitski.onelinestore.exception.ValidationException
import io.konform.validation.Validation

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
            if (errors.isNotEmpty()) throw ValidationException(errors)
        }
    }
}