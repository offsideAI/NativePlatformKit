package ai.offside.mobile.android.lib.glassbox

import android.view.View

/**
 * Stub for the monorepo `lib.glassbox` wrapper around the proprietary Glassbox analytics SDK.
 * All calls are no-ops; only [trackView] is used by `component.ui`.
 */
class LibGlassbox private constructor() {

    fun trackView(view: View) { /* no-op: Glassbox SDK not present in the standalone build */ }

    fun maskView(view: View) { /* no-op */ }

    companion object {
        private val instance = LibGlassbox()

        @JvmStatic
        fun getInstance(): LibGlassbox = instance
    }
}
