package by.kshakhnitski.onelinestore.validator

import by.kshakhnitski.onelinestore.exception.ValidationException

interface Validator<T> {
    /**
     * Validates the given object. If the object is not valid, throws [ValidationException].
     */
    fun validate(t: T)
}