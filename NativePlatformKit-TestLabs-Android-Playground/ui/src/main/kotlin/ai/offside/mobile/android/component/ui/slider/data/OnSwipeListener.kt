package ai.offside.mobile.android.component.ui.slider.data

import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout

/**
 * Interface for SliderComponent
 */
interface OnSwipeListener {
    /** Exposes action during swipe start for slider */
    fun onSwipeStart(sliderComponentLayout: SliderComponentLayout)

    /** Exposes action when user releases touch from slider view */
    fun onTouchUp()
}