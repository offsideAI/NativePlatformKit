package ai.offside.mobile.android.component.ui.expandcollapsegroup

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.ExpandCollapseLayoutBinding
import android.view.View
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.Transformation
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * ExpandCollapseGroup Component class
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class ExpandCollapseGroup @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding =
        ExpandCollapseLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    enum class ExpandCollapseState {
        EXPANDED,
        COLLAPSED,
        ;
    }

    private var expandState: ExpandCollapseState = ExpandCollapseState.COLLAPSED

    /**
     * Sets title and click listener on component
     * @param title
     * @param view
     */
    fun setGroupData(title: String, view: View) {
        binding.expandCollapseTextview.text = title

        binding.expandCollapseGroup.setOnClickListener {
            onClick(view)
        }
    }

    /**
     * Sets expand/collapse state and respective animations on click
     * @param view
     */
    private fun onClick(view: View) {
        when (expandState) {
            ExpandCollapseState.EXPANDED -> {
                collapse(view)
                rotateIconAntiClockwise()
                setAccessibilityDescription(R.string.collapsed)
                expandState = ExpandCollapseState.COLLAPSED
            }

            ExpandCollapseState.COLLAPSED -> {
                expand(view)
                rotateIconClockwise()
                setAccessibilityDescription(R.string.expanded)
                expandState = ExpandCollapseState.EXPANDED
            }
        }
    }

    /**
     * Rotates icon 180 degrees clockwise
     */
    private fun rotateIconClockwise() {
        val rotateAnimation = AnimationUtils.loadAnimation(
            this.context,
            R.anim.rotate_icon_clockwise
        )
        rotateAnimation.fillAfter = true
        binding.expandCollapseIcon.startAnimation(rotateAnimation)
    }

    /**
     * Rotates icon 180 degrees anti clockwise
     */
    private fun rotateIconAntiClockwise() {
        val rotateAnimation = AnimationUtils.loadAnimation(
            this.context,
            R.anim.rotate_icon_anticlockwise
        )
        rotateAnimation.fillAfter = true
        binding.expandCollapseIcon.startAnimation(rotateAnimation)
    }

    /**
     * Expands view animation
     * @param view
     */
    private fun expand(view: View) {
        val matchParentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec((view.parent as View).width, View.MeasureSpec.EXACTLY)
        val wrapContentMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(matchParentMeasureSpec, wrapContentMeasureSpec)
        val targetHeight = view.measuredHeight

        view.layoutParams.height = 1
        view.visibility = View.VISIBLE
        val expandAnimation: Animation = object : Animation(
        ) {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
                } else {
                    view.layoutParams.height = (targetHeight * interpolatedTime).toInt()
                }
                view.requestLayout()
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        expandAnimation.duration =
            (targetHeight / view.context.resources.displayMetrics.density).toInt().toLong()
        view.startAnimation(expandAnimation)
    }

    /**
     * Collapses view animation
     * @param view
     */
    private fun collapse(view: View) {
        val viewHeight = view.measuredHeight

        val collapseAnimation: Animation = object : Animation(
        ) {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                if (interpolatedTime == 1f) {
                    view.visibility = View.GONE
                } else {
                    view.layoutParams.height =
                        viewHeight - (viewHeight * interpolatedTime).toInt()
                    view.requestLayout()
                }
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        collapseAnimation.duration =
            (viewHeight / view.context.resources.displayMetrics.density).toInt().toLong()
        view.startAnimation(collapseAnimation)
    }

    /**
     * Sets custom accessibility description
     * @param expandStateDescription
     */
    private fun setAccessibilityDescription(expandStateDescription: Int) {
        ViewCompat.setAccessibilityHeading(binding.expandCollapseGroup, true)
        ViewCompat.setAccessibilityDelegate(binding.expandCollapseTextview,
            object : AccessibilityDelegateCompat() {
                override fun onInitializeAccessibilityNodeInfo(
                    host: View,
                    info: AccessibilityNodeInfoCompat
                ) {
                    super.onInitializeAccessibilityNodeInfo(host, info)
                    info.apply {
                        contentDescription = "${binding.expandCollapseTextview.text} , ${
                            context.getString(expandStateDescription)
                        }"
                    }

                }
            })
        binding.expandCollapseGroup.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_ACCESSIBILITY_FOCUSED)
    }

}