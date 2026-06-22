package ai.offside.mobile.android.component.ui.slider.data

import android.view.View
import androidx.annotation.IntRange

/**
 * Data model class for slider component
 *
 * @param data [SliderButtonData]
 */
class SliderComponentDataModel(val data: SliderComponentData) : SliderComponentData by data {
    /**Exposes first right button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val leftButtonVisibility: Int
        get() = when {
            data is SliderComponentData.LeftButton -> View.VISIBLE
            else -> View.GONE
        }
    /**Exposes first right button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val firstButtonVisibility: Int
        get() = when {
            data is SliderComponentData.OneRightButton -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes second right button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondButtonVisibility: Int
        get() = when {
            data is SliderComponentData.TwoRightButton -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes third right button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val thirdButtonVisibility: Int
        get() = when {
            data is SliderComponentData.ThreeRightButton -> View.VISIBLE
            else -> View.GONE
        }
}