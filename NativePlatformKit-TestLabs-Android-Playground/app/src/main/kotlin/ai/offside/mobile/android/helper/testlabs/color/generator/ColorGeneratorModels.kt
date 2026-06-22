package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.annotation.FloatRange
import androidx.annotation.Size
import androidx.annotation.IntRange
import android.graphics.drawable.ColorDrawable

sealed class ColorGeneratorModels {
    sealed class Input : ColorGeneratorModels() {
        data class Manual constructor(
            @IntRange(from = 0L, to = 255L) val redInt: Int = 0,
            @IntRange(from = 0L, to = 255L) val greenInt: Int = 0,
            @IntRange(from = 0L, to = 255L) val blueInt: Int = 0,
        ) : Input()
        data class Selector constructor(
            val hexString: String = "",
        ) : Input()
        data class Calculation constructor(
            @FloatRange(from = 0.0, to = 360.0) val hue: Float = 0F,
            @FloatRange(from = 0.0, to = 1.0) val saturation: Float = 0F,
            @FloatRange(from = 0.0, to = 1.0) val lightness: Float = 0F,
        ) : Input() {
            constructor(@Size(value = 3L) inputs: FloatArray) : this(
                hue = inputs[0],
                saturation = inputs[1],
                lightness = inputs[2]
            )
            val raw: FloatArray = floatArrayOf(hue, saturation, lightness)
            val display: String
                get() = "Hue $hue, Saturation: $saturation, Lightness: $lightness"
        }
    }
    sealed class Output : ColorGeneratorModels() {
        data class Generated(
            val input: Input,
            val drawable: ColorDrawable
        ) : Output()
    }
}