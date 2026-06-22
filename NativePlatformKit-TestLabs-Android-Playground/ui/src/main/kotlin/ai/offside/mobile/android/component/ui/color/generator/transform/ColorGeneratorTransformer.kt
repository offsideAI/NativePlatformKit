package ai.offside.mobile.android.component.ui.color.generator.transform

import androidx.annotation.Size

fun interface ColorGeneratorTransformer {
    operator fun invoke(
        @Size(value = 3L) input: FloatArray
    ): FloatArray
}