package ai.offside.mobile.android.component.application

import android.content.Context
import androidx.startup.Initializer

/**
 * androidx.startup initializer that bootstraps [ApplicationComponent].
 *
 * `UIComponentConfigInitializer` (in :ui) declares this class in its `dependencies()`,
 * so androidx.startup guarantees [ApplicationComponent] is ready before the UI component
 * configuration runs.
 */
class ApplicationComponentInitializer : Initializer<ApplicationComponent> {
    override fun create(context: Context): ApplicationComponent =
        ApplicationComponent.newInstance(context)

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}
