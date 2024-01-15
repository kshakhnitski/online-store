package by.kshakhnitski.onelinestore.user.validator

import by.kshakhnitski.onelinestore.user.exception.ValidationException

interface Validator<T> {
    /**
     * Validates the given object. If the object is not valid, throws [ValidationException].
     */
    fun validate(t: T)
}