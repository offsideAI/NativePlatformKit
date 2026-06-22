package ai.offside.mobile.android.component.ui.config

import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.NightMode
import ai.offside.mobile.android.component.application.ApplicationComponent
import ai.offside.mobile.android.component.storage.AppStorage
import ai.offside.mobile.android.component.storage.key.AppStorageKeyDefaultValue
import ai.offside.mobile.android.component.storage.key.AppStorageKeyName
import ai.offside.mobile.android.component.storage.read.AppStorageReadRequest
import ai.offside.mobile.android.component.storage.write.AppStorageWriteRequest
import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build

/**
 * Tracks the [appCompatNightMode] used by [AppCompatDelegate] to override (or adhere to)
 *   the configuration of the system
 * @param appCompatNightMode The [NightMode] [Int] for [AppCompatDelegate]
 * @param uiModeManagerNightMode The [Int] for [UiModeManager.setApplicationNightMode]
 */
enum class UIMode constructor(
    @NightMode val appCompatNightMode: Int,
    val uiModeManagerNightMode: Int,
) {
    SYSTEM(
        appCompatNightMode = AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM,
        uiModeManagerNightMode = UiModeManager.MODE_NIGHT_AUTO,
    ),
    LIGHT(
        appCompatNightMode = AppCompatDelegate.MODE_NIGHT_NO,
        uiModeManagerNightMode = UiModeManager.MODE_NIGHT_NO,
    ),
    DARK(
        appCompatNightMode = AppCompatDelegate.MODE_NIGHT_YES,
        uiModeManagerNightMode = UiModeManager.MODE_NIGHT_YES,
    ),
    ;

    fun writeToStorage() {
        AppStorage
            .getInstance()
            .writeAppStorage(
                appStorageWriteRequest = AppStorageWriteRequest.StringRequest(
                    keyName = uiModeAppStorageKeyName,
                    valueToWrite = name,
                )
            )
    }

    /**
     * Leveraged in calls to [androidx.activity.enableEdgeToEdge] and responsible for
     *   balancing which [UIMode] instance is being invoked as well as the necessary
     *   computations against the provided [resources] for [UIMode.SYSTEM]
     * @param resources The current [Resources] from which [Configuration.uiMode] is
     *   evaluated
     */
    fun isDarkMode(
        resources: Resources
    ): Boolean = when (this) {
        LIGHT -> false
        DARK -> true
        SYSTEM -> resources.run {
            (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        }
    }

    /**
     * Depending on [Build.VERSION.SDK_INT], we invoke methods upon [UiModeManager] or
     *   [AppCompatDelegate] to dynamically update the [UIMode] of the application
     *   in accordance with [appCompatNightMode]
     */
    fun applyUiMode() {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                (ApplicationComponent
                    .getInstance()
                    .applicationContext
                    .getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager)
                    ?.setApplicationNightMode(uiModeManagerNightMode)
            }
            else -> {
                AppCompatDelegate
                    .setDefaultNightMode(
                        appCompatNightMode
                    )
            }
        }
    }

    companion object {
        private val uiModeAppStorageKeyName: AppStorageKeyName =
            AppStorageKeyName.of(keyName = "UI_MODE")

        fun readFromSystem(): UIMode =
            (when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S ->
                    (ApplicationComponent
                        .getInstance()
                        .applicationContext
                        .getSystemService(Context.UI_MODE_SERVICE) as? UiModeManager)
                        ?.nightMode

                else -> AppCompatDelegate.getDefaultNightMode()
            }.let { nightMode ->
                entries.find { it.appCompatNightMode == nightMode || it.uiModeManagerNightMode == nightMode}
            } ?: LIGHT)

        fun readFromStorage(): UIMode =
            AppStorage
                .getInstance()
                .readAppStorage<String>(
                    appStorageReadRequest = AppStorageReadRequest.StringRequest(
                        keyName = uiModeAppStorageKeyName,
                        defaultValue = AppStorageKeyDefaultValue.StringValue(
                            defaultValue = LIGHT.name
                        )
                    )
                )
                .let { valueOf(it.value) }
    }
}