package ai.offside.mobile.android.component.ui.bottomsheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ai.offside.mobile.android.component.ui.R

object CustomBottomSheetBehavior {

    private const val BOTTOM_SHEET_MAX_HEIGHT_RATIO = 0.8
    private const val BOTTOM_SHEET_MIN_PEEK_HEIGHT = 0.12
    fun getMaxHeightOfBottomSheet(behaviorContext: Context): Int {
        return (behaviorContext.resources.displayMetrics.heightPixels * BOTTOM_SHEET_MAX_HEIGHT_RATIO).toInt()
    }

    fun getMinPeekHeightOfBottomSheet(behaviorContext: Context): Int {
        return (behaviorContext.resources.displayMetrics.heightPixels * BOTTOM_SHEET_MIN_PEEK_HEIGHT).toInt()
    }

    /**
     * Get the BottomSheet State by String for A11Y
     */
    fun getBehaviorStateByString(
        bottomSheetState: Int,
        behaviorContext: Context
    ): String =
        when (bottomSheetState) {
            BottomSheetBehavior.STATE_EXPANDED -> behaviorContext.resources.getString(R.string.a11y_bottom_sheet_state_collapse)
            BottomSheetBehavior.STATE_COLLAPSED -> behaviorContext.resources.getString(R.string.a11y_bottom_sheet_state_expand)
            else -> {
                behaviorContext.resources.getString(R.string.a11y_bottom_sheet_state_expand)
            }
        }


}