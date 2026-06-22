package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentPurple constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Purple {
    PURPLE_10(colorRes = R.color.sys_color_purple10),
    PURPLE_20(colorRes = R.color.sys_color_purple20),
    PURPLE_30(colorRes = R.color.sys_color_purple30),
    PURPLE_40(colorRes = R.color.sys_color_purple40),
    PURPLE_50(colorRes = R.color.sys_color_purple50),
    PURPLE_60(colorRes = R.color.sys_color_purple60),
    PURPLE_70(colorRes = R.color.sys_color_purple70),
    PURPLE_80(colorRes = R.color.sys_color_purple80),
    PURPLE_90(colorRes = R.color.sys_color_purple90),
    PURPLE_95(colorRes = R.color.sys_color_purple95),
    ;

}