package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentBlue constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Blue {
    BLUE_10(colorRes = R.color.sys_color_blue10),
    BLUE_20(colorRes = R.color.sys_color_blue20),
    BLUE_30(colorRes = R.color.sys_color_blue30),
    BLUE_40(colorRes = R.color.sys_color_blue40),
    BLUE_50(colorRes = R.color.sys_color_blue50),
    BLUE_60(colorRes = R.color.sys_color_blue60),
    BLUE_70(colorRes = R.color.sys_color_blue70),
    BLUE_80(colorRes = R.color.sys_color_blue80),
    BLUE_90(colorRes = R.color.sys_color_blue90),
    BLUE_95(colorRes = R.color.sys_color_blue95),
    ;

}