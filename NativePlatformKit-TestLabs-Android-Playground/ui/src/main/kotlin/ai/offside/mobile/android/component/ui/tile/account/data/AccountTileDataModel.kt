package ai.offside.mobile.android.component.ui.tile.account.data

import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel
import java.math.BigDecimal

/**
 * Data Model class for [AccountTileLayout]
 *
 * @param tileData
 * @param sliderData
 */
class AccountTileDataModel(
    val tileData: AccountTileData,
    val sliderData: SliderComponentDataModel
) : AccountTileData by tileData {

    /** [AccountTileBalanceState] for balance amount color state */
    val balanceAmountState: AccountTileBalanceState
        get() = if (tileData.balance >= BigDecimal.ZERO) AccountTileBalanceState.DEFAULT else {
            AccountTileBalanceState.NEGATIVE
        }

    /**Exposes more option kebab button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val moreOptionButtonVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetMoreInfoButton -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes primary button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val primaryButtonVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetPrimaryMicroButton -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes alternate button visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val alternateButtonVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetAlternateMicroButton -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes payment due view visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val paymentDueVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetPaymentDue -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes label value pair view visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val labelValuePairVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetLabelValuePair -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes second label value pair view visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondLabelValuePairVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetTwoLabelValuePair -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes label value pair view visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val rateChangeVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetRateChange -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes pricing update view visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val pricingUpdatesVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetPricingUpdate -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes button hint visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val buttonHintVisibility: Int
        get() = when {
            tileData is AccountTileData.AccountTileWidgetButtonHint -> View.VISIBLE
            else -> View.GONE
        }

}