package by.kshakhnitski.onelinestore.catalog.validator

import by.kshakhnitski.onelinestore.catalog.exception.ValidationException

interface Validator<T> {
    /**
     * Validates the given object. If the object is not valid, throws [ValidationException].
     */
    fun validate(t: T)
}