package ai.offside.mobile.android.component.ui.tile.account.data

import android.view.View

/**
 * Interface for clickable view on account tile
 */
interface AccountTileButton {
    /** [String] Label for the clickable view */
    val label: String

    /** OnClick action for the view */
    fun onButtonClick(view: View)
}