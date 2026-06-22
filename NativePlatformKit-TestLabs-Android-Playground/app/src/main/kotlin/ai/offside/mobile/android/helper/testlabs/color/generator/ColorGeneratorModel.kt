package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.annotation.ColorInt
import androidx.annotation.IntRange
import androidx.core.graphics.ColorUtils
import ai.offside.mobile.android.lib.timber.log
import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import ai.offside.mobile.android.helper.testlabs.color.generator.transform.ColorGeneratorTransform
import ai.offside.mobile.android.component.ui.color.generator.transform.ColorGeneratorTransformer

@OptIn(ExperimentalStdlibApi::class)
data class ColorGeneratorModel(
    @IntRange(from = 0L, to = 255L) val redInput: Int = 0,
    @IntRange(from = 0L, to = 255L) val greenInput: Int = 0,
    @IntRange(from = 0L, to = 255L) val blueInput: Int = 0,
    val transform: ColorGeneratorTransformer = ColorGeneratorTransform.FULL_ALPHA,
    val hexString: String = "",
) {
    init {
        val format = HexFormat {
            upperCase = true
            number {
                removeLeadingZeros = true
            }
        }
        log(level = Log.ERROR, tag = this::class.simpleName) {
            """
            Manually crafted HexString
            R - ${redInput.toHexString(format = format)}
            G - ${greenInput.toHexString(format = format)}
            B - ${blueInput.toHexString(format = format)}
            """.trimIndent()
        }
    }
    val colorInt: Int
        @SuppressLint("Range")
        get() = when {
            hexString.isNotBlank() -> Color.parseColor(hexString)
            else -> Color.rgb(redInput, greenInput, blueInput)
        }

    val hslInput: ColorGeneratorModels.Input.Calculation
        get() = floatArrayOf(0F, 0F, 0F).apply {
            ColorUtils.colorToHSL(colorInt, this)
        }.let { ColorGeneratorModels.Input.Calculation(inputs = it) }

    @get:ColorInt
    val mutatedColor: Int
        get() = ColorUtils.HSLToColor(
            transform(input = hslInput.raw)
        )

    val mutatedColorDrawable: ColorDrawable =
        ColorDrawable(mutatedColor)

    val mutatedHsl: ColorGeneratorModels.Input.Calculation
        get() = ColorGeneratorModels.Input.Calculation(
            inputs = transform(input = hslInput.raw)
        )

    val rgbString: String
        get() = "R - ${Color.red(colorInt)}, G - ${Color.green(colorInt)}, B - ${Color.blue(colorInt)}"

    val rgbMutatedString: String
        get() = "R - ${Color.red(mutatedColor)}, G - ${Color.green(mutatedColor)}, B - ${Color.blue(mutatedColor)}"
}