package ai.offside.mobile.android.component.ui.slider.data

/**
 * Data Provider for Slider Component
 */
class SliderComponentDataProvider {
    companion object {

        @JvmStatic
        fun sliderComponentDataWithOnlyLeftButton(
            leftButtonData: SliderButtonData
        ) = object : SliderComponentData.LeftButton {
            override val leftButtonData: SliderButtonData = leftButtonData
        }

        @JvmStatic
        fun getSliderComponentData(
            firstButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.OneRightButton {
                override val firstButtonData: SliderButtonData = firstButtonData
            }

        @JvmStatic
        fun getSliderComponentData(
            firstButtonData: SliderButtonData,
            secondButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.TwoRightButton {
                override val firstButtonData: SliderButtonData = firstButtonData
                override val secondButtonData: SliderButtonData = secondButtonData
            }

        @JvmStatic
        fun getSliderComponentData(
            firstButtonData: SliderButtonData,
            secondButtonData: SliderButtonData,
            thirdButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.ThreeRightButton {
                override val firstButtonData: SliderButtonData = firstButtonData
                override val secondButtonData: SliderButtonData = secondButtonData
                override val thirdButtonData: SliderButtonData = thirdButtonData
            }

        @JvmStatic
        fun getSliderComponentDataWithLeftButton(
            leftButtonData: SliderButtonData,
            firstButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.OneRightButton, SliderComponentData.LeftButton {
                override val leftButtonData: SliderButtonData = leftButtonData
                override val firstButtonData: SliderButtonData = firstButtonData
            }

        @JvmStatic
        fun getSliderComponentDataWithLeftButton(
            leftButtonData: SliderButtonData,
            firstButtonData: SliderButtonData,
            secondButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.TwoRightButton, SliderComponentData.LeftButton {
                override val leftButtonData: SliderButtonData = leftButtonData
                override val firstButtonData: SliderButtonData = firstButtonData
                override val secondButtonData: SliderButtonData = secondButtonData
            }

        @JvmStatic
        fun getSliderComponentDataWithLeftButton(
            leftButtonData: SliderButtonData,
            firstButtonData: SliderButtonData,
            secondButtonData: SliderButtonData,
            thirdButtonData: SliderButtonData
        ): SliderComponentData =
            object : SliderComponentData.ThreeRightButton, SliderComponentData.LeftButton {
                override val leftButtonData: SliderButtonData = leftButtonData
                override val firstButtonData: SliderButtonData = firstButtonData
                override val secondButtonData: SliderButtonData = secondButtonData
                override val thirdButtonData: SliderButtonData = thirdButtonData
            }
    }
}