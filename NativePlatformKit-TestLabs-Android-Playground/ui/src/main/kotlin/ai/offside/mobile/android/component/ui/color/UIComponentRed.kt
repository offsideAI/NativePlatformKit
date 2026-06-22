package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentRed constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Red {
    RED_10(colorRes = R.color.sys_color_red10),
    RED_20(colorRes = R.color.sys_color_red20),
    RED_30(colorRes = R.color.sys_color_red30),
    RED_40(colorRes = R.color.sys_color_red40),
    RED_50(colorRes = R.color.sys_color_red50),
    RED_60(colorRes = R.color.sys_color_red60),
    RED_70(colorRes = R.color.sys_color_red70),
    RED_80(colorRes = R.color.sys_color_red80),
    RED_90(colorRes = R.color.sys_color_red90),
    RED_95(colorRes = R.color.sys_color_red95),
    ;

}