package ai.offside.mobile.android.helper.testlabs.nav.util

import android.graphics.Rect
import android.view.View
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.updatePadding

/**
 * https://yatmanwong.medium.com/android-how-to-pan-the-page-up-more-25fc5c542a97
 *
 * Edge-to-Edge UI doesn't support the windowSoftInputMode,
 *  This has been added as per the suggestions from Medium link shared above
 * 1. enable edge-to-edge to occupy full screen-width
 * 2. Change the status bar color to DARK (WHITE text and icons)
 * 3. Add the status bar space as top-padding of the activity toot
 * 4. Add the compatibility for windowSoftInputMode
 */
class EdgeToEdgeHelper private constructor(activity: ComponentActivity) {
    private val mChildOfContent: View
    private var usableHeightPrevious = 0
    private val frameLayoutParams: FrameLayout.LayoutParams

    init {
        activity.enableEdgeToEdge()
        // Set status bar to dark
        WindowInsetsControllerCompat(activity.window, activity.window.decorView).apply {
            isAppearanceLightStatusBars = false
        }
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)

        // Add the status-bar space to the root of the view
        ViewCompat.setOnApplyWindowInsetsListener(mChildOfContent) { v, windowInsets ->
            val insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.updatePadding(top = insets.top)
            windowInsets
        }

        mChildOfContent.getViewTreeObserver()
            .addOnGlobalLayoutListener { possiblyResizeChildOfContent() }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent() {
        val usableHeightNow = computeUsableHeight()
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.getRootView().height
            val heightDifference = usableHeightSansKeyboard - usableHeightNow
            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightSansKeyboard - heightDifference
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightSansKeyboard
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return r.bottom - r.top
    }

    companion object {
        // For more information, see https://issuetracker.google.com/issues/36911528
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.
        fun assistActivity(activity: ComponentActivity) {
            EdgeToEdgeHelper(activity)
        }
    }
}
