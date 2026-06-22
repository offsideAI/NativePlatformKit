package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentYellow constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Yellow {
    YELLOW_10(colorRes = R.color.sys_color_yellow10),
    YELLOW_20(colorRes = R.color.sys_color_yellow20),
    YELLOW_30(colorRes = R.color.sys_color_yellow30),
    YELLOW_40(colorRes = R.color.sys_color_yellow40),
    YELLOW_50(colorRes = R.color.sys_color_yellow50),
    YELLOW_60(colorRes = R.color.sys_color_yellow60),
    YELLOW_70(colorRes = R.color.sys_color_yellow70),
    YELLOW_80(colorRes = R.color.sys_color_yellow80),
    YELLOW_90(colorRes = R.color.sys_color_yellow90),
    YELLOW_95(colorRes = R.color.sys_color_yellow95),
    ;

}