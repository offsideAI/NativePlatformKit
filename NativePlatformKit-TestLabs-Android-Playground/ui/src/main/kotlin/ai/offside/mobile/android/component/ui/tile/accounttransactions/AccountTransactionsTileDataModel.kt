package ai.offside.mobile.android.component.ui.tile.accounttransactions

import android.view.View
import androidx.annotation.IntRange

class AccountTransactionsTileDataModel(
    val transactionsTileData: AccountTransactionsTileData
) : AccountTransactionsTileData by transactionsTileData {

    /** Exposes transaction category visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val categoryVisibility: Int
        get() = when {
            transactionsTileData.category.isNullOrEmpty() -> View.GONE
            else -> View.VISIBLE
        }

    /** Exposes transaction running balance visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val runningBalanceVisibility: Int
        get() = when {
            transactionsTileData.runningBalance == null -> View.GONE
            else -> View.VISIBLE
        }

    /** Tile Divider visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val dividerVisibility: Int
        get() = when {
            transactionsTileData.showDivider -> View.VISIBLE
            else -> View.GONE
        }

    /** Space visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val spaceVisibility: Int
        get() = when {
            transactionsTileData.singleLineTransaction -> View.VISIBLE
            else -> View.GONE
        }

    /** Deposit icon visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val depositIconVisibility: Int
        get() = when(accountTransactionType) {
            AccountTransactionType.DEPOSIT_TRANSACTION -> View.VISIBLE
            else -> View.GONE
        }
}