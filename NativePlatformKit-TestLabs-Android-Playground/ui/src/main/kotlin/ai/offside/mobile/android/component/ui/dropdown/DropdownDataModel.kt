package ai.offside.mobile.android.component.ui.dropdown

import android.view.View
import androidx.annotation.IntRange
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.extensions.addSpaceBetweenCharacter

/**
 * DropdownDataModel class bound to dropdown layout
 * @param data DropdownData
 */
class DropdownDataModel(
    val data: DropdownData
) : DropdownData by data {

    /**
     * Set selected item on dropdown tile
     * @param item CompoundButtonData
     */
    fun setItem(item: CompoundButtonData){
        selectedItem = item
    }

    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryInfoVisibility: Int
        get() = if (selectedItem.secondaryDescription.isNotBlank() || selectedItem.tertiaryDescription.isNotBlank()) {
            View.VISIBLE
        } else {
            View.GONE
        }

    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val tertiaryInfoVisibility: Int
        get() = if (selectedItem.tertiaryDescription.isNotBlank()) {
            View.VISIBLE
        } else {
            View.GONE
        }

    /**
     * Accessibility description for primary info on dropdown tile
     */
    val primaryInfoAccessibilityDescription: String
        get() = if (selectedItem.primaryText.contains("(?i)[x]\\d{4}$".toRegex())) {
            val primaryInfoList = selectedItem.primaryText.split('x', ignoreCase = true)
            "${primaryInfoList[0]} ending in ${primaryInfoList[1].addSpaceBetweenCharacter()}"
        } else {
            selectedItem.primaryText
        }
}