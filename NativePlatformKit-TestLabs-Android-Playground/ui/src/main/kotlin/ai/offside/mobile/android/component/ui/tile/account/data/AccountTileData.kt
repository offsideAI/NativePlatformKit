package ai.offside.mobile.android.component.ui.tile.account.data

import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters
import java.math.BigDecimal

/**
 * Interface for customizable account tile
 */
interface AccountTileData {
    /** [String] display name for the tile */
    val displayName: String

    /** [String] account name  */
    val accountName: String

    /** [String] masked account number*/
    val maskedAccountNumber:String

    /** [BigDecimal] balance for the tile */
    val balance: BigDecimal

    /** [String] balance label for the tile */
    val balanceLabel: String

    /** Onclick action for the tile */
    fun onClick(tile: AccountTileData)

    sealed interface MicroButton : AccountTileData {
        interface PrimaryMicroButton : MicroButton{}
        interface AlternateMicroButton : MicroButton{}
    }

    interface AccountTileWidgetMoreInfoButton {
        /** [AccountTileButton] for more button */
        val moreButton: AccountTileButton
    }

    /**
     * Widget interface for primary micro button on account tile
     */
    interface AccountTileWidgetPrimaryMicroButton {
        /** [AccountTileButton] Data for micro button */
        val primaryButtonData: AccountTileButton
    }

    /**
     * Widget interface for alternate micro button on account tile
     */
    interface AccountTileWidgetAlternateMicroButton {
        /** [AccountTileButton] Data for micro button */
        val alternateButton: AccountTileButton
    }

    /**
     * Widget interface for payment due amount and date info
     */
    interface AccountTileWidgetPaymentDue {
        /** [String] to display due amount */
        val paymentDueAmountLabel: String

        /** [String] to display due date */
        val paymentDueDateLabel: String

        /** [AccountTilePaymentStatus] for account*/
        val paymentStatus: AccountTilePaymentStatus

        /**Exposes payment due info visibility */
        @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
        val dueInfoVisibility: Int
            get() = when {
                paymentStatus == AccountTilePaymentStatus.HAS_DUE -> View.VISIBLE
                else -> View.GONE
            }

        /**Exposes no payment due label visibility */

        @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
        val noPaymentDueVisibility: Int
            get() = when {
                paymentStatus == AccountTilePaymentStatus.NO_DUE -> View.VISIBLE
                else -> View.GONE
            }

        /**Exposes payment overdue label visibility */
        @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
        val paymentOverdueVisibility: Int
            get() = when {
                paymentStatus == AccountTilePaymentStatus.OVERDUE -> View.VISIBLE
                else -> View.GONE
            }
    }

    /**
     * Widget interface for displaying rate change on account tile
     */
    interface AccountTileWidgetRateChange {
        /** [BigDecimal] amount to display rate amount */
        val rateAmount: BigDecimal

        /** [Double] percentage to display rate percentage */
        val ratePercentage: Double

        /** [String] label to display change interval */
        val changeLabel: String

        /** [AccountTileBalanceState] for rate amount color state */
        val rateAmountState: AccountTileBalanceState
            get() = if (rateAmount >= BigDecimal.ZERO) {
                AccountTileBalanceState.POSITIVE
            } else {
                AccountTileBalanceState.NEGATIVE
            }

        /** Function to fetch display label for rate change */
        val rateDisplay: String
            get() = if (ratePercentage > 0) {
                "${MaterialTextViewBindingAdapters.getCurrencyText(rateAmount)}(+$ratePercentage%)"
            } else {
                "${MaterialTextViewBindingAdapters.getCurrencyText(rateAmount)}($ratePercentage%)"
            }
    }

    /**
     * Widget interface for displaying pricing updates
     */
    interface AccountTileWidgetPricingUpdate {
        /** [String] label to display price updates */
        val pricingUpdateLabel: String

        /** [AccountTileButton] data for learn more */
        val learnMoreButton: AccountTileButton
    }

    /**
     * Widget interface for displaying label value pair
     */
    interface AccountTileWidgetLabelValuePair {
        /** [Pair] containing label and value */
        val labelValuePair: AccountTileLabelBigDecimalValuePair
    }

    /**
     * Widget interface for displaying two label value pair
     */
    interface AccountTileWidgetTwoLabelValuePair : AccountTileWidgetLabelValuePair {
        /** [Pair] containing label and value */
        val secondLabelValuePair: AccountTileLabelBigDecimalValuePair
    }

    /**
     * Widget interface for displaying button hint text
     */
    interface AccountTileWidgetButtonHint {
        /** [String] label text to display on the left side of the button*/
        val hintText: String
    }

    /**
     * Interface [AccountTileWithPrimaryMicroButton] to inherit [AccountTile] tile and [AccountTileWidgetPrimaryMicroButton] widget
     */
    interface AccountTileWithPrimaryMicroButton : AccountTileData, AccountTileWidgetMoreInfoButton,
        AccountTileWidgetPrimaryMicroButton

    /**
     * Interface [AccountTileWithAlternateMicroButton] to inherit [AccountTile] tile and [AccountTileWidgetAlternateMicroButton] widget
     */
    interface AccountTileWithAlternateMicroButton : AccountTileData,
        AccountTileWidgetMoreInfoButton,
        AccountTileWidgetAlternateMicroButton

    /**
     * Interface [AccountTileWithPaymentDueAndButton] to inherit [AccountTile] tile,
     * [AccountTileWidgetPaymentDue] widget and [AccountTileWidgetPrimaryMicroButton] widget
     */
    interface AccountTileWithPaymentDueAndButton : AccountTileData,
        AccountTileWidgetMoreInfoButton,
        AccountTileWidgetPaymentDue, AccountTileWidgetPrimaryMicroButton

    /**
     * Interface [AccountTileWithRateChange] to inherit [AccountTile] tile and [AccountTileWidgetRateChange] widget
     */
    interface AccountTileWithRateChange : AccountTileData, AccountTileWidgetMoreInfoButton,
        AccountTileWidgetRateChange

    /**
     * Interface [AccountTileWithRateChangeAndPricingUpdates] to inherit [AccountTileWithRateChange] and [AccountTileWidgetPricingUpdate] widget
     */
    interface AccountTileWithRateChangeAndPricingUpdates : AccountTileWithRateChange,
        AccountTileWidgetPricingUpdate

    /**
     * Interface [AccountTileWithRateChangeAndPricingUpdates] to inherit [AccountTile] tile and [AccountTileWidgetLabelValuePair] widget
     */
    interface AccountTileWithLabelValuePair : AccountTileData, AccountTileWidgetMoreInfoButton,
        AccountTileWidgetLabelValuePair

    /**
     * Interface [AccountTileWithTwoLabelValuePairAndPricingUpdates] to inherit [AccountTile] tile,
     * [AccountTileWidgetPricingUpdate] and  [AccountTileWidgetTwoLabelValuePair] widget
     */
    interface AccountTileWithTwoLabelValuePairAndPricingUpdates : AccountTileData,
        AccountTileWidgetTwoLabelValuePair, AccountTileWidgetPricingUpdate {
    }

    /**
     * Interface [AccountTileWithTwoLabelValuePairButtonAndButtonHint] to inherit [AccountTile] tile,
     * [AccountTileWidgetTwoLabelValuePair] and  [AccountTileWidgetPrimaryMicroButton] widget
     */
    interface AccountTileWithTwoLabelValuePairButtonAndButtonHint : AccountTileData,
        AccountTileWidgetMoreInfoButton,
        AccountTileWidgetTwoLabelValuePair, AccountTileWidgetPrimaryMicroButton,
        AccountTileWidgetButtonHint
}
