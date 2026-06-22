package ai.offside.mobile.android.component.ui.tile.ecommercewallettoolset.data

import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * [EcommerceWalletToolSetData] provides the data to render Ecommerce Wallet tool-set on the account details
 *  - If the feature list has less than or equal to 2 then it will be rendered as list-actions
 *  - If the list is more than or equal to 2 then it will be rendered as tool-set tabs
 *   (layout_constraintHorizontal_chainStyle as [spread]) the container space
 *  - At max only 4 features (first 4) will be rendered in the UI
 *
 * @param featuresList: contains list of [ListItemData.ListItemWithIcon]
 * @param customizable: contains the label and action as [ListItemData.SimpleListItem]
 */
data class EcommerceWalletToolSetData(
    val featuresList: List<ListItemData.ListItemWithIcon>,
    val customizable: ListItemData.SimpleListItem
) {
    /**
     * Exposes the visibility of Customize option
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val getCustomizableVisibility get() = if (customizable.enabled) View.VISIBLE else View.GONE
}

