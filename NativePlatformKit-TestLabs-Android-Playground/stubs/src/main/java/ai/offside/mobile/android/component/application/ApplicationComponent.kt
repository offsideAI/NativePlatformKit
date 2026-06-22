package ai.offside.mobile.android.component.application

import android.app.Application
import android.content.Context

/**
 * Stub replacement for the monorepo `component.application` module.
 *
 * Only the surface actually used by `component.ui` is reproduced:
 *  - [getInstance] singleton accessor
 *  - [applicationContext] / [application]
 *
 * Initialized via [ApplicationComponentInitializer] through androidx.startup,
 * exactly as the original module was.
 */
class ApplicationComponent private constructor(
    val applicationContext: Context,
) {
    /** The original exposed an `ApplicationComponentApp`; an [Application] is sufficient here. */
    val application: Application
        get() = applicationContext as Application

    companion object {
        @Volatile
        private var instance: ApplicationComponent? = null

        @JvmStatic
        fun getInstance(): ApplicationComponent =
            instance ?: error(
                "ApplicationComponent not initialized. " +
                    "Ensure androidx.startup runs UIComponentConfigInitializer (which depends on " +
                    "ApplicationComponentInitializer).",
            )

        fun newInstance(applicationContext: Context): ApplicationComponent =
            ApplicationComponent(applicationContext.applicationContext)
                .also { instance = it }
    }
}
