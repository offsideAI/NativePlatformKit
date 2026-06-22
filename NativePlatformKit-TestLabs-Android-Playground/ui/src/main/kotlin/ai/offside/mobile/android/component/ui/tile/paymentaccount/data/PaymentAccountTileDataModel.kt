package ai.offside.mobile.android.component.ui.tile.paymentaccount.data

import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileButton
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelBigDecimalValuePair
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelValuePair

/**
 * Data Model class for [PaymentAccountTileLayout]
 *
 * @param displayName
 * @param accountType
 * @param monthlyPaymentDue
 * @param monthlyPaymentDate
 * @param balance
 * @param lastPaymentAmount
 * @param lastPaymentDate
 * @param primaryButton
 * @param secondaryButton
 * @param tertiaryButton
 */
class PaymentAccountTileDataModel(
    override val displayName: String,
    override val accountType: String,
    override val monthlyPaymentDue: AccountTileLabelBigDecimalValuePair,
    override val monthlyPaymentDate: AccountTileLabelValuePair,
    override val balance: AccountTileLabelValuePair,
    override val lastPaymentAmount: AccountTileLabelBigDecimalValuePair,
    override val lastPaymentDate: AccountTileLabelValuePair,
    override val viewAccount: AccountTileButton,
    override val primaryButton: PaymentAccountTileButton,
    override val secondaryButton: PaymentAccountTileButton,
    override val tertiaryButton: PaymentAccountTileButton
) : PaymentAccountTileData {

    /**Exposes [PaymentAccountTileData.primaryButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val primaryButtonVisibility: Int
        get() = when {
            primaryButton.visibility -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [PaymentAccountTileData.secondaryButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryButtonVisibility: Int
        get() = when {
            secondaryButton.visibility -> View.VISIBLE
            else -> View.GONE
        }

    /**Exposes [PaymentAccountTileData.tertiaryButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val tertiaryButtonVisibility: Int
        get() = when {
            tertiaryButton.visibility -> View.VISIBLE
            else -> View.GONE
        }
}