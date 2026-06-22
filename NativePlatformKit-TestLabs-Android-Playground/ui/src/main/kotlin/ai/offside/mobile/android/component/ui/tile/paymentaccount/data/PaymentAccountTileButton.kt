package ai.offside.mobile.android.component.ui.tile.paymentaccount.data

import android.view.View

/**
 * [PaymentAccountTileButton] button
 */
interface PaymentAccountTileButton {

    /** [String] Label for the clickable view */
    val label: String

    /** [Boolean] visibility of clickable view */
    var visibility: Boolean

    /** OnClick action for the view */
    fun onButtonClick(view: View)
}