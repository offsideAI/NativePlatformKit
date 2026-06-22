package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import ai.offside.mobile.android.component.ui.R

enum class UIComponentGraphite constructor(
    @ColorRes override val colorRes: Int
) : UIComponentColor.Graphite {
    GRAPHITE_10(colorRes = R.color.sys_color_graphite10),
    GRAPHITE_20(colorRes = R.color.sys_color_graphite20),
    GRAPHITE_30(colorRes = R.color.sys_color_graphite30),
    GRAPHITE_40(colorRes = R.color.sys_color_graphite40),
    GRAPHITE_50(colorRes = R.color.sys_color_graphite50),
    GRAPHITE_60(colorRes = R.color.sys_color_graphite60),
    GRAPHITE_70(colorRes = R.color.sys_color_graphite70),
    GRAPHITE_80(colorRes = R.color.sys_color_graphite80),
    GRAPHITE_90(colorRes = R.color.sys_color_graphite90),
    GRAPHITE_95(colorRes = R.color.sys_color_graphite95),
    ;

}