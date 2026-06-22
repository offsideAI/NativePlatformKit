package ai.offside.mobile.android.component.ui.config

import androidx.annotation.StyleRes
import ai.offside.mobile.android.component.storage.AppStorage
import ai.offside.mobile.android.component.storage.key.AppStorageKeyDefaultValue
import ai.offside.mobile.android.component.storage.key.AppStorageKeyName
import ai.offside.mobile.android.component.storage.read.AppStorageReadRequest
import ai.offside.mobile.android.component.storage.write.AppStorageWriteRequest
import ai.offside.mobile.android.component.ui.R

/**
 * The various pre-defined [StyleRes] values associated with various
 *   [R.style] definitions for [AppTheme]s
 * @param styleName A [StyleRes] for the particular [AppTheme]
 */
enum class AppTheme constructor(
    @StyleRes val styleName: Int
) {
    RETAIL(R.style.Theme_Offside_Base_Retail),
    PRIVATE_BANK(R.style.Theme_Offside_Base_PrivateBank),
    ;

    fun writeToStorage() {
        AppStorage
            .getInstance()
            .writeAppStorage(
                appStorageWriteRequest = AppStorageWriteRequest.StringRequest(
                    keyName = appThemeAppStorageKeyName,
                    valueToWrite = name,
                )
            )
    }

    companion object {
        private val appThemeAppStorageKeyName: AppStorageKeyName =
            AppStorageKeyName.of(keyName = "APP_THEME")

        fun readFromStorage(): AppTheme =
            AppStorage
                .getInstance()
                .readAppStorage<String>(
                    appStorageReadRequest = AppStorageReadRequest.StringRequest(
                        keyName = appThemeAppStorageKeyName,
                        defaultValue = AppStorageKeyDefaultValue.StringValue(
                            defaultValue = RETAIL.name
                        )
                    )
                )
                .let { valueOf(it.value) }
    }
}