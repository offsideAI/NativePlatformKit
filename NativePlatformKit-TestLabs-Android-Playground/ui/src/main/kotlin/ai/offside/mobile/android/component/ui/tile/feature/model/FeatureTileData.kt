package ai.offside.mobile.android.component.ui.tile.feature.model

import androidx.annotation.DrawableRes
import androidx.annotation.Keep

@Keep
data class FeatureTileData (
    val title: CharSequence,
    val subTitle: String,
    @DrawableRes val icon: Int,
    val badgeCount: Int = 0,
    val dividerVisible: Boolean = false,
    val tileClickAction: () -> Unit = { },
    val moreClickAction: () -> Unit = { }
)