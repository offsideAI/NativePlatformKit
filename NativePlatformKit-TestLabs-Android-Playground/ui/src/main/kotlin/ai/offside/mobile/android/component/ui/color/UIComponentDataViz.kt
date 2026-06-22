package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.color.generator.transform.ColorGeneratorTransformer

enum class UIComponentDataViz constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.DataViz {
    NAVY(colorRes = R.color.data_viz_color_seed_navy),
    BLUE(colorRes = R.color.data_viz_color_seed_blue),
    INCOME(colorRes = R.color.data_viz_color_seed_income),
    ;

    override val transformationStops: Int
        get() = when (this) {
            NAVY -> 24
            INCOME -> 16
            else -> 8
        }

    override val transformations: List<ColorGeneratorTransformer>
        get() = buildList(capacity = transformationStops) {
            for (i in 1 .. transformationStops) {
                add(
                    ColorGeneratorTransformer { input: FloatArray ->
                        floatArrayOf(
                            input[0],
                            input[1],
                            input[2].times(i.div(transformationStops.toFloat()))
                        )
                    }
                )
            }
        }
}