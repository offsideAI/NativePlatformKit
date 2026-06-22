package ai.offside.mobile.android.component.ui.config

import ai.offside.mobile.android.component.application.ApplicationComponent
import ai.offside.mobile.android.component.application.activity.AppActivityLifecycleCallbacks

internal class UIComponentConfigImpl constructor(
    private val applicationComponent: ApplicationComponent,
) : UIComponentConfig {
    private val appActivityCallbacks: AppActivityLifecycleCallbacks =
        AppActivityLifecycleCallbacks.newInstance(
            onActivityCreated = { activity, savedInstanceState ->
                activity.apply {
                    setTheme(AppTheme.readFromStorage().styleName)
                    UIMode.readFromStorage().applyUiMode()

                    title = savedInstanceState
                        ?.getBundle("local_config")
                        ?.getBoolean("suppress_activity_title")
                        ?.let { isPresent -> if (isPresent) { "" } else { title } }
                        ?: title
                }
            }
        )

    init {
        applicationComponent
            .application
            .registerActivityLifecycleCallbacks(appActivityCallbacks)
    }
}