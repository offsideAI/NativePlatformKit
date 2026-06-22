package ai.offside.mobile.android.component.ui.listitem.data

import android.view.View
import android.widget.RadioGroup
import androidx.annotation.DrawableRes
import ai.offside.mobile.android.component.ui.a11y.CheckBoxA11y
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData


/** Data Interface of List Item */
sealed interface ListItemData {
    /** [String] label for list Item*/
    val label: CharSequence

    /** [Boolean] enabled status for list Item*/
    val enabled: Boolean get() = true

    /** Interface for List Item with Button */
    sealed interface ListItemButton : ListItemData {
        /** Drawable Resource for list item button icon */
        @get:DrawableRes
        val iconRes: Int
        /** [String] label for button */
        val buttonLabel: String
        /** [ListItemButtonType] button type*/
        val buttonType : ListItemButtonType
    }

    /** Interface for List Item With Icon */
    sealed interface ListItemIcon : ListItemData {
        /** Drawable Resource for list item icon */
        @get:DrawableRes
        val iconRes: Int
        /** [ListItemIconTint] Tint variant for ListItemIcon*/
        val iconTint: ListItemIconTint
    }

    /** Interface for List Item With Emphasized Icon */
    sealed interface ListItemEmphasizedIcon : ListItemData {
        /** Drawable Resource for emphasized list item icon */
        @get:DrawableRes
        val iconRes: Int
        /** [ListItemIconTint] Tint variant for ListItemIcon*/
        val iconTint: ListItemIconTint
    }

    /** Interface for List Item With status */
    sealed interface ListItemStatus : ListItemData {
        /** [String] for status text view */
        val status: String
    }

    /** Interface for List Item With Switch */
    sealed interface ListItemSwitch : ListItemData {
        fun onToggle(view: View, isChecked: Boolean)
    }

    /** Interface for List Item With Radio Group */
    sealed interface ListItemRadioGroup : ListItemData {
        /** [CompoundButtonData] list for radio button items */
        val items: List<CompoundButtonData>

        /** Click handle for radio button */
        fun onRadioButtonChecked(group: RadioGroup, selectedIndex: Int)
    }

    /** Interface for List Item With Checkbox */
    sealed interface ListItemCheckboxGroup : ListItemData {
        /** [CompoundButtonData] list for checkbox items */
        val items: List<CompoundButtonData>

        /** Click handle for checkbox */
        fun onCheckBoxChecked(
            checkBox: CheckBoxA11y,
            checkedItems: List<CompoundButtonData>
        )
    }

    /** Interface for Clickable List Item */
    fun interface Clickable {
        /** OnClick handler */
        fun onClick(view: View)
    }

    /** Widget Interface for Secondary Description text */
    sealed interface ListItemWidgetSecondaryText {
        /** [String] fro secondary string */
        val secondaryText: String
    }

    interface SimpleListItem : ListItemData, ListItemWidgetSecondaryText, Clickable
    interface ListItemWithButton : ListItemButton, Clickable
    interface ListItemWithIcon : ListItemIcon, Clickable
    interface ListItemWithEmphasizedIcon : ListItemEmphasizedIcon, Clickable
    interface ListItemWithStatus : ListItemStatus, ListItemWidgetSecondaryText, Clickable
    interface ListItemWithSwitch : ListItemSwitch
    interface RadioGroupListItem : ListItemRadioGroup
    interface CheckboxGroupListItem : ListItemCheckboxGroup
}