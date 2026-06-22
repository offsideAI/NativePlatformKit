package ai.offside.mobile.android.component.storage

import android.content.Context
import ai.offside.mobile.android.component.application.ApplicationComponent
import ai.offside.mobile.android.component.storage.key.AppStorageKeyName
import ai.offside.mobile.android.component.storage.read.AppStorageReadRequest
import ai.offside.mobile.android.component.storage.write.AppStorageWriteRequest

/** Result wrapper exposing `.value`, matching how `component.ui` consumes reads. */
data class AppStorageReadResult<T>(val value: T)

/**
 * Stub for the monorepo `component.storage` module, backed by [android.content.SharedPreferences]
 * so that values written by the testlabs (selected theme, UI mode, language) actually persist.
 *
 * `component.ui` only exercises the `String` request variants, which is all that is implemented.
 */
class AppStorage private constructor(context: Context) {

    private val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    fun <T> readAppStorage(appStorageReadRequest: AppStorageReadRequest): AppStorageReadResult<T> =
        when (appStorageReadRequest) {
            is AppStorageReadRequest.StringRequest -> {
                val value = prefs.getString(
                    appStorageReadRequest.keyName.keyName,
                    appStorageReadRequest.defaultValue.defaultValue,
                ) ?: appStorageReadRequest.defaultValue.defaultValue
                AppStorageReadResult(value as T)
            }
        }

    fun writeAppStorage(appStorageWriteRequest: AppStorageWriteRequest) {
        when (appStorageWriteRequest) {
            is AppStorageWriteRequest.StringRequest ->
                prefs.edit()
                    .putString(appStorageWriteRequest.keyName.keyName, appStorageWriteRequest.valueToWrite)
                    .apply()
        }
    }

    fun contains(keyName: AppStorageKeyName): Boolean = prefs.contains(keyName.keyName)

    fun containsKey(keyName: AppStorageKeyName): Boolean = prefs.contains(keyName.keyName)

    companion object {
        private const val PREFS_NAME = "ui_testlabs_app_storage"

        @Volatile
        private var instance: AppStorage? = null

        @JvmStatic
        fun getInstance(): AppStorage =
            instance ?: synchronized(this) {
                instance ?: AppStorage(ApplicationComponent.getInstance().applicationContext)
                    .also { instance = it }
            }
    }
}
