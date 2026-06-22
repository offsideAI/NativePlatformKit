package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentSand constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Sand {
    SAND_10(colorRes = R.color.sys_color_sand10),
    SAND_20(colorRes = R.color.sys_color_sand20),
    SAND_30(colorRes = R.color.sys_color_sand30),
    SAND_40(colorRes = R.color.sys_color_sand40),
    SAND_50(colorRes = R.color.sys_color_sand50),
    SAND_60(colorRes = R.color.sys_color_sand60),
    SAND_70(colorRes = R.color.sys_color_sand70),
    SAND_80(colorRes = R.color.sys_color_sand80),
    SAND_90(colorRes = R.color.sys_color_sand90),
    SAND_95(colorRes = R.color.sys_color_sand95),
    ;

}