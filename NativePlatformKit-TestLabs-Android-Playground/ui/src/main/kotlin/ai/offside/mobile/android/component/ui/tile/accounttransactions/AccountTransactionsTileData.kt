package ai.offside.mobile.android.component.ui.tile.accounttransactions

import java.math.BigDecimal

/**
 * Interface for account transactions tile
 */
interface AccountTransactionsTileData {

    /** [String] transaction description */
    val description: String

    /** [BigDecimal] transaction amount */
    val amount: BigDecimal

    /** [BigDecimal] transaction running balance */
    val runningBalance: BigDecimal?

    /** [String] transaction category */
    val category: String?

    /** [Boolean] show divider for tile */
    val showDivider: Boolean

    /** [Int] amount text color */
    val amountTextColor: Int

    /** [Boolean] is pending transaction */
    val isPendingTransaction: Boolean

    /** [Boolean] is single line transaction */
    val singleLineTransaction: Boolean

    /** [AccountTransactionType] transaction type enum */
    val accountTransactionType: AccountTransactionType

    /** Onclick action for the tile
     *
     * @param tileData AccountTransactionsTileData to pass on click
     */
    fun onClick(tileData: AccountTransactionsTileData)
}