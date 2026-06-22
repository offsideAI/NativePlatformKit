package ai.offside.mobile.android.component.ui.dropdown

import android.view.View
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData

/**
 * Interface for Dropdown Tile
 */
interface DropdownData {
    val label: String
    var selectedItem: CompoundButtonData
    fun onClick(view: View)
}