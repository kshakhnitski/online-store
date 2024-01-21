package by.kshakhnitski.onelinestore.auth.validator

import by.kshakhnitski.onelinestore.auth.exception.ValidationException

interface Validator<T> {
    /**
     * Validates the given object. If the object is not valid, throws [ValidationException].
     */
    fun validate(t: T)
}