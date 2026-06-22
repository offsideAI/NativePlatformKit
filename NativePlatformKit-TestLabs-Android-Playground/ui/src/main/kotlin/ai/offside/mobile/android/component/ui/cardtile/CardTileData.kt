package ai.offside.mobile.android.component.ui.cardtile

import androidx.annotation.Keep

@Keep
data class CardTileData(
    val primaryDisplayInfo: PrimaryDisplayInfo = PrimaryDisplayInfo(),
    val secondaryDisplayInfo: String = "",
    val cardStatus: String = "",
    val cardActionLabel: String = "",
    val cardArtUrl: String = ""
)

@Keep
data class PrimaryDisplayInfo(
    val primaryInfo: String = "",
    val primarySuffixInfo: String = "",
    val isShowMiddleEllipses: Boolean = false
)
