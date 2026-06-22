package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentOrange constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Orange {
    ORANGE_10(colorRes = R.color.sys_color_orange10),
    ORANGE_20(colorRes = R.color.sys_color_orange20),
    ORANGE_30(colorRes = R.color.sys_color_orange30),
    ORANGE_40(colorRes = R.color.sys_color_orange40),
    ORANGE_50(colorRes = R.color.sys_color_orange50),
    ORANGE_60(colorRes = R.color.sys_color_orange60),
    ORANGE_70(colorRes = R.color.sys_color_orange70),
    ORANGE_80(colorRes = R.color.sys_color_orange80),
    ORANGE_90(colorRes = R.color.sys_color_orange90),
    ORANGE_95(colorRes = R.color.sys_color_orange95),
    ;

}