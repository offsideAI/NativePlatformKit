package ai.offside.mobile.android.component.ui.tile.account.data

import android.content.Context
import android.graphics.Color
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.R

/**
 * [AccountTileBalanceState] for account tile balance view
 */
enum class AccountTileBalanceState constructor(
    val color: Int
) {
    DEFAULT(R.attr.offside_onSurface),
    POSITIVE(R.attr.offside_tertiary),
    NEGATIVE(R.attr.offside_error),
    ;

    fun getColorValue(context: Context): Int =
        MaterialColors.getColor(context, color, Color.BLACK)
}