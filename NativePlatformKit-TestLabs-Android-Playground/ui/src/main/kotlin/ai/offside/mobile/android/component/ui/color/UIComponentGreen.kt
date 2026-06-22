package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentGreen constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Green {
    GREEN_10(colorRes = R.color.sys_color_green10),
    GREEN_20(colorRes = R.color.sys_color_green20),
    GREEN_30(colorRes = R.color.sys_color_green30),
    GREEN_40(colorRes = R.color.sys_color_green40),
    GREEN_50(colorRes = R.color.sys_color_green50),
    GREEN_60(colorRes = R.color.sys_color_green60),
    GREEN_70(colorRes = R.color.sys_color_green70),
    GREEN_80(colorRes = R.color.sys_color_green80),
    GREEN_90(colorRes = R.color.sys_color_green90),
    GREEN_95(colorRes = R.color.sys_color_green95),
    ;

}