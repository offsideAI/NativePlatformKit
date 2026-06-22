package ai.offside.mobile.android.component.storage.key

/** Type-safe storage key name. */
class AppStorageKeyName private constructor(val keyName: String) {
    companion object {
        fun of(keyName: String): AppStorageKeyName = AppStorageKeyName(keyName)
    }
}

/** Default value supplied with a read request. Only the String variant is used by component.ui. */
sealed class AppStorageKeyDefaultValue {
    data class StringValue(val defaultValue: String) : AppStorageKeyDefaultValue()
}
