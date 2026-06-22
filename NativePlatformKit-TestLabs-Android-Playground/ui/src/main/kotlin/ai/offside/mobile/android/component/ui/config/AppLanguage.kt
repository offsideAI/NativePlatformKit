package ai.offside.mobile.android.component.ui.config

import androidx.annotation.Size
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ai.offside.mobile.android.component.application.ApplicationComponent
import ai.offside.mobile.android.component.storage.AppStorage
import ai.offside.mobile.android.component.storage.key.AppStorageKeyDefaultValue
import ai.offside.mobile.android.component.storage.key.AppStorageKeyName
import ai.offside.mobile.android.component.storage.read.AppStorageReadRequest
import ai.offside.mobile.android.component.storage.write.AppStorageWriteRequest
import ai.offside.mobile.android.component.ui.config.AppLanguage.Companion.LANGUAGE_TAG_SIZE
import java.util.Locale
import android.content.res.Configuration

/**
 * The application language setting adjusting the JVM [Locale]
 *   and [AppCompatDelegate.setApplicationLocales].  [AppLanguage.SYSTEM]
 *   reflects [Locale.getDefault] whereas alternative definitions
 *   are responsible for providing the appropriate [languageCode] and
 *   [languageTag] as required by [Locale]
 * @param languageCode The two-letter, lowercase, representation of a [Locale.getCountry]
 * @param languageTag The two-letter, lowercase, two-letter, uppercase, hyphenated identifier
 *   for a particular language and country [Locale].  This field has a [Size]
 *   of [LANGUAGE_TAG_SIZE]
 */
enum class AppLanguage constructor(
    @Size(value = LANGUAGE_CODE_SIZE) val languageCode: String = Locale.getDefault().country,
    @Size(value = LANGUAGE_TAG_SIZE) val languageTag: String = Locale.getDefault().toLanguageTag()
) {
    SYSTEM,
    ENGLISH(languageCode = "en", languageTag = "en-US"),
    SPANISH(languageCode = "es", languageTag = "es-US"),
    ;

    fun writeToStorage() {
        AppStorage
            .getInstance()
            .writeAppStorage(
                appStorageWriteRequest = AppStorageWriteRequest.StringRequest(
                    keyName = appLanguageAppStorageKey,
                    valueToWrite = name
                )
            )
    }

    /**
     * Retrieves a [StringRes] from a [Configuration] tied to
     *   the chosen [AppLanguage]
     * @param stringRes A [StringRes] to retrieve translated
     */
    fun getString(
        @StringRes stringRes: Int
    ): String =
        ApplicationComponent
            .getInstance()
            .applicationContext
            .run {
                createConfigurationContext(
                    Configuration().apply {
                        setLocale(this@AppLanguage.locale)
                    }
                )
            }
            .getString(stringRes)

    /**
     * Retrieves a [StringRes] from a [Configuration] tied to
     *   the chosen [AppLanguage].  Accepts [arguments] to pass
     *   to [getString]
     * @param stringRes A [StringRes] to retrieve translated
     * @param arguments Any required replacement values
     */
    fun getString(
        @StringRes stringRes: Int,
        vararg arguments: Any
    ): String =
        ApplicationComponent
            .getInstance()
            .applicationContext
            .run {
                createConfigurationContext(
                    Configuration().apply {
                        setLocale(this@AppLanguage.locale)
                    }
                )
            }
            .getString(stringRes, arguments)

    /**
     * Applies a [LocaleListCompat] instance to the [AppCompatDelegate]
     *   class derived from the chosen [AppLanguage]
     */
    fun applyToAppCompatDelegate() {
        AppCompatDelegate
            .setApplicationLocales(
                localeListCompat
            )
    }

    /** Retrieves the [Locale] representing this [AppLanguage] */
    val locale: Locale
        get() = when (this) {
            SYSTEM -> Locale.getDefault()
            ENGLISH -> Locale.US
            SPANISH -> Locale(languageCode, Locale.US.country)
        }

    /** The necessary [LocaleListCompat] for this [AppLanguage] */
    private val localeListCompat: LocaleListCompat
        get() = when (this) {
            SYSTEM -> LocaleListCompat.getEmptyLocaleList()
            else -> LocaleListCompat.forLanguageTags(languageTag)
        }

    override fun toString(): String =
        """
        AppLanguage{
            name = $name
            resourceName = $languageCode
            localeTag = $languageTag
            locale = $locale
            localeListCompat = $localeListCompat
        }
        """.trimIndent()

    companion object {
        private const val LANGUAGE_CODE_SIZE: Long = 2L
        private const val LANGUAGE_TAG_SIZE: Long = 5L
        private const val SHARED_PREFERENCES_KEY: String = "APP_LANGUAGE"
        private val appLanguageAppStorageKey: AppStorageKeyName =
            AppStorageKeyName.of(keyName = SHARED_PREFERENCES_KEY)

        fun readFromStorage(): AppLanguage =
            AppStorage
                .getInstance()
                .readAppStorage<String>(
                    appStorageReadRequest = AppStorageReadRequest.StringRequest(
                        keyName = appLanguageAppStorageKey,
                        defaultValue = AppStorageKeyDefaultValue.StringValue(
                            defaultValue = SYSTEM.name
                        )
                    )
                )
                .let { valueOf(it.value) }

        fun readFromAppDelegate(): AppLanguage =
            (entries
                .find { it.localeListCompat == AppCompatDelegate.getApplicationLocales() }
                ?: SYSTEM)
                .also { it.writeToStorage() }
    }
}