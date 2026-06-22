package ai.offside.mobile.android.component.ui.slider.data

import android.view.View

/**
 * Interface for SliderComponent
 */
sealed interface SliderComponentData {
    /** [SliderComponentData] for menu with left button */
    interface LeftButton : SliderComponentData {
        /** [SliderButtonData] for left menu button */
        val leftButtonData: SliderButtonData
    }

    /** [SliderComponentData] for menu with one right button */
    interface OneRightButton : SliderComponentData {
        /** [SliderButtonData] for first right button */
        val firstButtonData: SliderButtonData
    }

    /** [SliderComponentData] for menu with two right button */
    interface TwoRightButton : OneRightButton {
        /** [SliderButtonData] for second right button */
        val secondButtonData: SliderButtonData
    }

    /** [SliderComponentData] for menu with three right button */
    interface ThreeRightButton : TwoRightButton {
        /** [SliderButtonData] for third right button */
        val thirdButtonData: SliderButtonData
    }
}