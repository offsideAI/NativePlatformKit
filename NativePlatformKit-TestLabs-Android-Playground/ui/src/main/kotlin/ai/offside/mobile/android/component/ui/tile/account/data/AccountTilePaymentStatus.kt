package ai.offside.mobile.android.component.ui.tile.account.data

import ai.offside.mobile.android.component.ui.R

/**
 * Enum class for account payment tile due status
 */
enum class AccountTilePaymentStatus(val labelRes: Int) {
    NO_DUE(R.string.account_tile_no_min_payment_due),
    OVERDUE(R.string.account_tile_payment_overdue),
    HAS_DUE(-1)
}