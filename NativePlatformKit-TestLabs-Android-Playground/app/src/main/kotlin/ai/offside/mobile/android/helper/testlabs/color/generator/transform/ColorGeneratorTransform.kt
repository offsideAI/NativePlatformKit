package ai.offside.mobile.android.helper.testlabs.color.generator.transform

import android.graphics.ColorMatrix
import ai.offside.mobile.android.component.ui.color.generator.transform.ColorGeneratorTransformer

enum class ColorGeneratorTransform constructor(

) : ColorGeneratorTransformer {
    ZERO_ALPHA,
    ONE_TENTH_ALPHA,
    TWO_TENTHS_ALPHA,
    THREE_TENTHS_ALPHA,
    FOUR_TENTHS_ALPHA,
    FIVE_TENTHS_ALPHA,
    SIX_TENTHS_ALPHA,
    SEVEN_TENTHS_ALPHA,
    EIGHT_TENTHS_ALPHA,
    NINE_TENTHS_ALPHA,
    FULL_ALPHA,
    ;

    val colorMatrix: ColorMatrix
        get() = ColorMatrix().apply {
            setScale(
                1F,
                1F,
                1F,
                ordinal.toFloat().div(10)
            )
        }

    override fun invoke(input: FloatArray): FloatArray =
        floatArrayOf(
            input[0], input[1],
            when (this) {
                ZERO_ALPHA -> 0F
                FULL_ALPHA -> input[2]
                ONE_TENTH_ALPHA -> input[2].times(0.1F)
                TWO_TENTHS_ALPHA -> input[2].times(0.2F)
                THREE_TENTHS_ALPHA -> input[2].times(0.3F)
                FOUR_TENTHS_ALPHA -> input[2].times(0.4F)
                FIVE_TENTHS_ALPHA -> input[2].times(0.5F)
                SIX_TENTHS_ALPHA -> input[2].times(0.6F)
                SEVEN_TENTHS_ALPHA -> input[2].times(0.7F)
                EIGHT_TENTHS_ALPHA -> input[2].times(0.8F)
                NINE_TENTHS_ALPHA -> input[2].times(0.9F)
            }
        )

    companion object {
        fun transforms(): List<ColorGeneratorTransform> =
            entries.reversed().filterNot { it == FULL_ALPHA || it == ZERO_ALPHA }
    }
}