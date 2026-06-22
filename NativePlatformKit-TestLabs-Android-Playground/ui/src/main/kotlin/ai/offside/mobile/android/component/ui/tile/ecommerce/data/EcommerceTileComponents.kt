package ai.offside.mobile.android.component.ui.tile.ecommerce.data

import android.view.View

/**
 * [EcommerceTileComponents] button
 */
interface EcommerceTileComponents {

    /** [String] Label for the clickable view */
    val label: String

    /** [Boolean] visibility of clickable view */
    var visibility: Boolean

    /**
     * Modal will show non clickable components [EcommerceComponentModel]
     */
    data class EcommerceComponentModel(
        override val label: String,
        override var visibility: Boolean
    ) : EcommerceTileComponents

}