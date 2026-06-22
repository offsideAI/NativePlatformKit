package ai.offside.mobile.android.component.ui.config

import ai.offside.mobile.android.component.application.ApplicationComponent
import android.content.Context

interface UIComponentConfig {
    companion object {
        private lateinit var instance: UIComponentConfig

        internal fun newInstance(
            applicationContext: Context,
            applicationComponent: ApplicationComponent
        ): UIComponentConfig =
            UIComponentConfigImpl(
                applicationComponent = applicationComponent,
            ).also { instance = it }

        @JvmStatic
        fun getInstance(): UIComponentConfig = instance
    }
}