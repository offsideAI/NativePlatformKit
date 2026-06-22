package ai.offside.mobile.android.component.ui.extensions

/**
 * Provides extension functions to convert between integers and enum constants.
 */

/**
 * Convert an integer to its corresponding enum constant.
 * @param T the type of the enum.
 * @receiver The integer value to be converted.
 * @return The enum constant corresponding to the integer value, or null if no such constant exists.
 * @throws IllegalArgumentException if T is not an enum class.
 */
inline fun <reified T : Enum<T>> Int.toEnum(): T? {
    return enumValues<T>().firstOrNull() { it.ordinal == this }
}

/**
 * Convert an enum constant to its corresponding integer value.
 * @param T the type of the enum.
 * @receiver The enum constant to be converted.
 * @return The ordinal value of the enum constant.
 * @throws IllegalArgumentException if T is not an enum class.
 */
inline fun <reified T : Enum<T>> T.toInt(): Int {
    return this.ordinal
}