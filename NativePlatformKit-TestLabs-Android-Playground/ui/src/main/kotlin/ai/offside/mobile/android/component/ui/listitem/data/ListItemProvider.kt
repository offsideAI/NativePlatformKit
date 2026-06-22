package ai.offside.mobile.android.component.ui.listitem.data

import android.view.View
import android.widget.RadioGroup
import androidx.annotation.DrawableRes
import androidx.core.content.res.ResourcesCompat
import ai.offside.mobile.android.component.ui.a11y.CheckBoxA11y
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData

/**
 * Return the instance of [SimpleListItem]
 *
 * @param label
 * @param secondaryText
 * @param enabled
 * @param onClickAction
 */
fun getSimpleListItem(
    label: String,
    secondaryText: String = "",
    enabled: Boolean = true,
    onClickAction: (view: View) -> Unit
) = object : ListItemData.SimpleListItem {
    override val label: String = label
    override val secondaryText = secondaryText
    override val enabled = enabled
    override fun onClick(view: View) {
        onClickAction(view)
    }
}

/**
 * Return the instance of [ListItemWithButton]
 *
 * @param label
 * @param buttonLabel
 * @param iconRes Icon Res for button
 * @param enabled
 * @param onClickAction
 */
fun getListItemWithButton(
    label: String,
    buttonLabel: String,
    @DrawableRes iconRes: Int = ResourcesCompat.ID_NULL,
    buttonType: ListItemButtonType = ListItemButtonType.PRIMARY,
    enabled: Boolean = true,
    onClickAction: (view: View) -> Unit
) = object : ListItemData.ListItemWithButton {
    override val label: String = label
    override val iconRes: Int = iconRes
    override val buttonLabel: String = buttonLabel
    override val buttonType: ListItemButtonType = buttonType
    override val enabled = enabled
    override fun onClick(view: View) {
        onClickAction(view)
    }
}

/**
 * Return the instance of [ListItemWithIcon]
 *
 * @param label
 * @param iconRes
 * @param iconTint
 * @param enabled
 * @param onClickAction
 */
fun getListItemWithIcon(
    label: CharSequence,
    @DrawableRes iconRes: Int,
    iconTint: ListItemIconTint = ListItemIconTint.NONE,
    enabled: Boolean = true,
    onClickAction: (view: View) -> Unit
) = object : ListItemData.ListItemWithIcon {
    override val label: CharSequence = label
    override val iconRes: Int = iconRes
    override val iconTint = iconTint
    override val enabled = enabled
    override fun onClick(view: View) {
        onClickAction(view)
    }
}

/**
 * Return the instance of [ListItemWithEmphasizedIcon]
 *
 * @param label
 * @param iconRes
 * @param iconTint
 * @param enabled
 * @param onClickAction
 */
fun getListItemWithEmphasizedIcon(
    label: String,
    @DrawableRes iconRes: Int,
    iconTint: ListItemIconTint = ListItemIconTint.NONE,
    enabled: Boolean = true,
    onClickAction: (view: View) -> Unit
) = object : ListItemData.ListItemWithEmphasizedIcon {
    override val label: String = label
    override val iconRes: Int = iconRes
    override val iconTint = iconTint
    override val enabled = enabled
    override fun onClick(view: View) {
        onClickAction(view)
    }
}

/**
 * Return the instance of [ListItemWithStatus]
 *
 * @param label
 * @param status
 * @param secondaryText
 * @param enabled
 * @param onClickAction
 */
fun getListItemWithStatus(
    label: String,
    status: String,
    secondaryText: String = "",
    enabled: Boolean = true,
    onClickAction: (view: View) -> Unit,
) = object : ListItemData.ListItemWithStatus {
    override val label = label
    override val status = status
    override val enabled = enabled
    override val secondaryText = secondaryText
    override fun onClick(view: View) {
        onClickAction(view)
    }
}


/**
 * Return the instance of [ListItemWithSwitch]
 *
 * @param label
 * @param enabled
 * @param onToggleAction
 */
fun getListItemWithSwitch(
    label: String,
    enabled: Boolean = true,
    onToggleAction: (view: View, isChecked: Boolean) -> Unit,
) = object : ListItemData.ListItemWithSwitch {
    override val label: String = label
    override val enabled = enabled
    override fun onToggle(view: View, isChecked: Boolean) {
        onToggleAction(view, isChecked)
    }
}

/**
 * Return the instance of [RadioGroupListItem]
 *
 * @param label String label for accessibility content description
 * @param items CompoundButtonData for radio button items
 * @param enabled
 * @param onRadioButtonCheckedAction
 */
fun getRadioGroupListItem(
    label: String,
    items: List<CompoundButtonData>,
    enabled: Boolean = true,
    onRadioButtonCheckedAction: (group: RadioGroup, selectedIndex: Int) -> Unit,
) = object : ListItemData.RadioGroupListItem {
    override val label: String = label
    override val enabled = enabled
    override val items = items
    override fun onRadioButtonChecked(group: RadioGroup, selectedIndex: Int) {
        onRadioButtonCheckedAction(group, selectedIndex)
    }
}

/**
 * Return the instance of [CheckboxGroupListItem]
 *
 * @param label String label for accessibility content description
 * @param items CompoundButtonData for checkbox items
 * @param enabled
 * @param onCheckBoxCheckedAction
 */
fun getCheckboxListItem(
    label: String,
    items: List<CompoundButtonData>,
    enabled: Boolean = true,
    onCheckBoxCheckedAction: (checkBox: CheckBoxA11y, checkedItems: List<CompoundButtonData>) -> Unit
) = object : ListItemData.CheckboxGroupListItem {
    override val label: String = label
    override val enabled = enabled
    override val items = items
    override fun onCheckBoxChecked(
        checkBox: CheckBoxA11y,
        checkedItems: List<CompoundButtonData>
    ) {
        onCheckBoxCheckedAction(checkBox, checkedItems)
    }
}