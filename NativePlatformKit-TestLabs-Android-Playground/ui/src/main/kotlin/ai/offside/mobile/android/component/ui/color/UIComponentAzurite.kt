package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentAzurite constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Azurite {
    AZURITE_10(colorRes = R.color.sys_color_azurite10),
    AZURITE_20(colorRes = R.color.sys_color_azurite20),
    AZURITE_30(colorRes = R.color.sys_color_azurite30),
    AZURITE_40(colorRes = R.color.sys_color_azurite40),
    AZURITE_50(colorRes = R.color.sys_color_azurite50),
    AZURITE_60(colorRes = R.color.sys_color_azurite60),
    AZURITE_70(colorRes = R.color.sys_color_azurite70),
    AZURITE_80(colorRes = R.color.sys_color_azurite80),
    AZURITE_90(colorRes = R.color.sys_color_azurite90),
    AZURITE_95(colorRes = R.color.sys_color_azurite95),
    ;

}