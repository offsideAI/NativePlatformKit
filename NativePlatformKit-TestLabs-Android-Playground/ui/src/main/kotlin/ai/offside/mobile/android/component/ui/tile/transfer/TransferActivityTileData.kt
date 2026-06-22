package ai.offside.mobile.android.component.ui.tile.transfer

import android.view.View
import java.math.BigDecimal

/**
 * Interface for Transfer Activity tile
 */
interface TransferActivityTileData {

    /** [String] transaction toAccount */
    val toAccount: String

    /** [BigDecimal] transaction amount */
    val amount: BigDecimal

    /** [BigDecimal] transaction status */
    val status: String?

    /** [String] transaction fromAccount */
    val fromAccount: String?

    /** [String] transaction date */
    val date: String?

    /** [Boolean] is recurring transaction */
    val isRecurring: Boolean

    /** [Boolean] show divider for tile */
    val showDivider: Boolean

    /** [Boolean] is single line transaction */
    val singleLineTransaction: Boolean

    /** Onclick action for the tile
     *
     * @param tileView The container [View]
     * @param tileData TransferActivityTileData to pass on click
     */
    fun onClick(tileView: View, tileData: TransferActivityTileData)
}