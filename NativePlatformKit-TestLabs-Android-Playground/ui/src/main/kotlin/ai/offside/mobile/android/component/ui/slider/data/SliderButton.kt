package ai.offside.mobile.android.component.ui.slider.data

import androidx.annotation.DrawableRes

/**
 * Interface for [SliderButtonData]
 */
interface SliderButtonData {
    /** [String] label for the button */
    val label: String

    /** [Int] Icon resource for the button */
    @get:DrawableRes
    val icon: Int

    /** Button click action */
    fun onButtonClick(button: SliderButtonData)
}