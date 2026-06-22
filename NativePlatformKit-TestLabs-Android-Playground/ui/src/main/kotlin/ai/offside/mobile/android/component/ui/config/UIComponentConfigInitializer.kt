package ai.offside.mobile.android.component.ui.config

import androidx.startup.Initializer
import ai.offside.mobile.android.component.application.ApplicationComponent
import ai.offside.mobile.android.component.application.ApplicationComponentInitializer
import android.content.Context

class UIComponentConfigInitializer : Initializer<UIComponentConfig> {
    override fun create(applicationContext: Context): UIComponentConfig =
        UIComponentConfig
            .newInstance(
                applicationContext = applicationContext,
                applicationComponent = ApplicationComponent.getInstance(),
            )

    override fun dependencies(): List<Class<out Initializer<*>>> =
        listOf(
            ApplicationComponentInitializer::class.java
        )
}