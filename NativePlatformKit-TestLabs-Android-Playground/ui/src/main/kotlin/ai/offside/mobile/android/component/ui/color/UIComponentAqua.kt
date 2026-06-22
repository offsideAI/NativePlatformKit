package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentAqua constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Aqua {
    AQUA_10(colorRes = R.color.sys_color_aqua10),
    AQUA_20(colorRes = R.color.sys_color_aqua20),
    AQUA_30(colorRes = R.color.sys_color_aqua30),
    AQUA_40(colorRes = R.color.sys_color_aqua40),
    AQUA_50(colorRes = R.color.sys_color_aqua50),
    AQUA_60(colorRes = R.color.sys_color_aqua60),
    AQUA_70(colorRes = R.color.sys_color_aqua70),
    AQUA_80(colorRes = R.color.sys_color_aqua80),
    AQUA_90(colorRes = R.color.sys_color_aqua90),
    AQUA_95(colorRes = R.color.sys_color_aqua95),
    ;

}