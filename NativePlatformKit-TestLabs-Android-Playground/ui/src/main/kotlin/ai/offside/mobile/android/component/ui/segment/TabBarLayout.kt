package ai.offside.mobile.android.component.ui.segment

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout

/**
 * [TabBarLayout] custom [com.google.android.material.tabs.TabLayout] to avoid the extra bottom spacing on the tabs
 *      when the content height is lesser than the Material Calculated height
 * @param context
 * @param attrs
 * @param defStyleAttr
 */
class TabBarLayout(context: Context, attrs: AttributeSet) :
    TabLayout(context, attrs) {

    /**
     * Set the TabLayout height to the first child-tab height. This will avoid the extra space at bottom
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // Force the height of this ViewGroup to be the same height of the tabStrip
        (getChildAt(0) as? ViewGroup)?.measuredHeight?.let {
            setMeasuredDimension(measuredWidth, it)
        }
    }
}