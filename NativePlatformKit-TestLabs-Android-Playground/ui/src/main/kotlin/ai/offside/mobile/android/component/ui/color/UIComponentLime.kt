package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentLime constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Lime {
    LIME_10(colorRes = R.color.sys_color_lime10),
    LIME_20(colorRes = R.color.sys_color_lime20),
    LIME_30(colorRes = R.color.sys_color_lime30),
    LIME_40(colorRes = R.color.sys_color_lime40),
    LIME_50(colorRes = R.color.sys_color_lime50),
    LIME_60(colorRes = R.color.sys_color_lime60),
    LIME_70(colorRes = R.color.sys_color_lime70),
    LIME_80(colorRes = R.color.sys_color_lime80),
    LIME_90(colorRes = R.color.sys_color_lime90),
    LIME_95(colorRes = R.color.sys_color_lime95),
    ;

}