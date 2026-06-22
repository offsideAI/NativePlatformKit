package ai.offside.mobile.android.component.ui.tile.paymentaccount.data

import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileButton
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelBigDecimalValuePair
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileLabelValuePair

/**
 * Interface class for [PaymentsAccountTile]
 */
interface PaymentAccountTileData {
    /** [String] for display name */
    val displayName: String

    /** [String] for account type */
    val accountType: String

    /** [AccountTileLabelBigDecimalValuePair] for minimum payment due label and value */
    val monthlyPaymentDue: AccountTileLabelBigDecimalValuePair

    /** [AccountTileLabelValuePair] for payment scheduled/due date label and value */
    val monthlyPaymentDate: AccountTileLabelValuePair

    /** [Pair] for balance label and value */
    val balance: AccountTileLabelValuePair

    /** [Pair] for last payment amount label and value */
    val lastPaymentAmount: AccountTileLabelBigDecimalValuePair

    /** [Pair] for last payment date label and value */
    val lastPaymentDate: AccountTileLabelValuePair

    /** [AccountTileButton] for view account button */
    val viewAccount: AccountTileButton

    /** [PaymentAccountTile] for primary button */
    val primaryButton: PaymentAccountTileButton

    /** [PaymentAccountTile] for secondary button */
    val secondaryButton: PaymentAccountTileButton

    /** [PaymentAccountTile] for tertiary button */
    val tertiaryButton: PaymentAccountTileButton
}