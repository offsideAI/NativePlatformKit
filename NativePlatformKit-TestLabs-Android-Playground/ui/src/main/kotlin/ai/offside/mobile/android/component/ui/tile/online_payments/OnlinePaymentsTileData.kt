package ai.offside.mobile.android.component.ui.tile.online_payments

import java.math.BigDecimal

/**
 * Interface for online_payments tile data
 */
interface OnlinePaymentsTileData {

    val onlinePaymentsDisplayName: String

    val onlinePaymentsAmount: BigDecimal

    val onlinePaymentsType: OnlinePaymentsTileDataModel.OnlinePaymentsType

    val onlinePaymentsImageRes: Int

    val onlinePaymentsImageUrl: String

    val onlinePaymentsFromAccount: String

    val onlinePaymentsDueDate: String

    /** Onclick action for the tile */
    fun onTileClick(tileData: OnlinePaymentsTileData) {}

    /** Onclick action for the Edit burron */
    fun onEditButtonClick(tileData: OnlinePaymentsTileData) {}

    /** Onclick action for the primary button */
    fun onPrimaryButtonClick(tileData: OnlinePaymentsTileData) {}

    /** Onclick action for the secondary button */
    fun onSecondaryButtonClick(tileData: OnlinePaymentsTileData) {}

}