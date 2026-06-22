package ai.offside.mobile.android.component.ui.tile.account.data

import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters
import java.math.BigDecimal

/**
 * Data class for Account Tile Label and [BigDecimal] Value pair
 */
data class AccountTileLabelBigDecimalValuePair(val label: String, val value: BigDecimal) {

    /**
     * Converts [BigDecimal] value to [String] and returns them as [AccountTileLabelValuePair]
     */
    fun getPairInString() = AccountTileLabelValuePair(label, MaterialTextViewBindingAdapters.getCurrencyText(value, false))
}