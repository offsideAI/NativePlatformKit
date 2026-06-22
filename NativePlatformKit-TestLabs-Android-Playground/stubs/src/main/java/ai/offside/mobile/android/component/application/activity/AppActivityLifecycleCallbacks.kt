package ai.offside.mobile.android.component.application.activity

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * Stub for the monorepo helper that builds an [Application.ActivityLifecycleCallbacks]
 * from per-event lambdas. `component.ui` only supplies [onActivityCreated]; the rest
 * default to no-ops.
 */
class AppActivityLifecycleCallbacks private constructor(
    private val onActivityCreated: (Activity, Bundle?) -> Unit,
    private val onActivityStarted: (Activity) -> Unit,
    private val onActivityResumed: (Activity) -> Unit,
    private val onActivityPaused: (Activity) -> Unit,
    private val onActivityStopped: (Activity) -> Unit,
    private val onActivitySaveInstanceState: (Activity, Bundle) -> Unit,
    private val onActivityDestroyed: (Activity) -> Unit,
) : Application.ActivityLifecycleCallbacks {

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) =
        onActivityCreated.invoke(activity, savedInstanceState)

    override fun onActivityStarted(activity: Activity) = onActivityStarted.invoke(activity)
    override fun onActivityResumed(activity: Activity) = onActivityResumed.invoke(activity)
    override fun onActivityPaused(activity: Activity) = onActivityPaused.invoke(activity)
    override fun onActivityStopped(activity: Activity) = onActivityStopped.invoke(activity)
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) =
        onActivitySaveInstanceState.invoke(activity, outState)

    override fun onActivityDestroyed(activity: Activity) = onActivityDestroyed.invoke(activity)

    companion object {
        fun newInstance(
            onActivityCreated: (Activity, Bundle?) -> Unit = { _, _ -> },
            onActivityStarted: (Activity) -> Unit = { },
            onActivityResumed: (Activity) -> Unit = { },
            onActivityPaused: (Activity) -> Unit = { },
            onActivityStopped: (Activity) -> Unit = { },
            onActivitySaveInstanceState: (Activity, Bundle) -> Unit = { _, _ -> },
            onActivityDestroyed: (Activity) -> Unit = { },
        ): AppActivityLifecycleCallbacks = AppActivityLifecycleCallbacks(
            onActivityCreated = onActivityCreated,
            onActivityStarted = onActivityStarted,
            onActivityResumed = onActivityResumed,
            onActivityPaused = onActivityPaused,
            onActivityStopped = onActivityStopped,
            onActivitySaveInstanceState = onActivitySaveInstanceState,
            onActivityDestroyed = onActivityDestroyed,
        )
    }
}
