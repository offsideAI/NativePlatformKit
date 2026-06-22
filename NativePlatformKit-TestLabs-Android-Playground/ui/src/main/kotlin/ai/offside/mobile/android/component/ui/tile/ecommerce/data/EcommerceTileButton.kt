package ai.offside.mobile.android.component.ui.tile.ecommerce.data

import android.view.View

/**
 * [EcommerceTileButton] button
 */
sealed interface EcommerceTileButton {

    /** [String] Label for the clickable view */
    val label: String

    /** [Boolean] visibility of clickable view */
    var visibility: Boolean

    /** OnClick action for the view */
    val onActionListener: ((View) -> Unit)


    /**
     * Modal will show buttons [EcommerceButtonModel]
     */
    data class EcommerceButtonModel(
        override val label: String,
        override var visibility: Boolean,
        override val onActionListener: ((View) -> Unit)
    ) : EcommerceTileButton

}