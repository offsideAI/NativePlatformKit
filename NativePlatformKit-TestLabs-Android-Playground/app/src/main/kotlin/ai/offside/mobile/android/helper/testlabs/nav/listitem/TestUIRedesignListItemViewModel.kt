package ai.offside.mobile.android.helper.testlabs.nav.listitem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemButtonType
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemIconTint
import ai.offside.mobile.android.component.ui.listitem.data.getCheckboxListItem
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithButton
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithEmphasizedIcon
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithIcon
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithStatus
import ai.offside.mobile.android.component.ui.listitem.data.getListItemWithSwitch
import ai.offside.mobile.android.component.ui.listitem.data.getRadioGroupListItem
import ai.offside.mobile.android.component.ui.listitem.data.getSimpleListItem

class TestUIRedesignListItemViewModel : ViewModel() {

    private val _emphasizedListItems = MutableLiveData<List<ListItemData>>().apply {
        value = listOf(
            getListItemWithEmphasizedIcon(
                "Emphasized List Item",
                R.drawable.ic_informative_qr_code,
                ListItemIconTint.PRIMARY
            ) {},
            getListItemWithEmphasizedIcon(
                "Emphasized List Item",
                R.drawable.ic_decorative_person,
            ) {},
        )
    }

    private val _listItems = MutableLiveData<List<ListItemData>>().apply {
        value = listOf(
            getListItemWithIcon(
                "List Item",
                R.drawable.ic_informative_qr_code,
                ListItemIconTint.PRIMARY
            ) {},
            getListItemWithIcon(
                "Multiline element that wraps within an individual tile",
                R.drawable.ic_decorative_placeholder_regular,
            ) {},
            getListItemWithIcon(
                "List Item",
                R.drawable.ic_decorative_placeholder_regular,
                enabled = false
            ) {},
            getListItemWithSwitch(
                "List Item",
            ) { view, isChecked -> },
            getListItemWithStatus(
                "List Item",
                "Status",
            ) {},
            getListItemWithStatus(
                "List Item",
                "Status",
                secondaryText = "Secondary Text"
            ) {},
            getSimpleListItem(
                "List Item",
            ) {},
            getSimpleListItem(
                "List Item",
                secondaryText = "Secondary Text"
            ) {},
        )
    }

    private val _buttonListItem = MutableLiveData<List<ListItemData>>().apply {
        value = listOf(
            getListItemWithButton(
                "List Item primary button",
                "Test Button",
                buttonType = ListItemButtonType.PRIMARY
            ) { view ->
            },
            getListItemWithButton(
                "List Item primary button",
                "Test Button",
                buttonType = ListItemButtonType.PRIMARY,
                iconRes = R.drawable.ic_actionable_edit
            ) { view ->
            },
            getListItemWithButton(
                "List Item alternate button",
                "Test Button",
                buttonType = ListItemButtonType.ALTERNATE
            ) { view ->
            },
            getListItemWithButton(
                "List Item alternate button",
                "Test Button",
                buttonType = ListItemButtonType.ALTERNATE,
                iconRes = R.drawable.ic_actionable_edit
            ) { view ->
            },
        )
    }

    private val _radioListItem = MutableLiveData<ListItemData.RadioGroupListItem>().apply {
        value = getRadioGroupListItem(
            "Radio Group Label",
            listOf(
                CompoundButtonData("List Item Radio Button", enabled = false),
                CompoundButtonData("List Item Radio Button 1"),
                CompoundButtonData("List Item Radio Button 2", "Secondary Description"),
                CompoundButtonData(
                    "List Item Radio Button 3",
                    "Secondary Description",
                    "Tertiary Description"
                )
            ),
        ) { buttonGroup, selectedIndex ->
        }
    }

    private val _checkboxItem = MutableLiveData<ListItemData.CheckboxGroupListItem>().apply {
        value = getCheckboxListItem(
            "Checkbox Label",
            listOf(
                CompoundButtonData("List Item Checkbox", enabled = false),
                CompoundButtonData("List Item Checkbox 1"),
                CompoundButtonData("List Item Checkbox 2", "Secondary Description"),
                CompoundButtonData(
                    "List Item Checkbox 3",
                    "Secondary Description",
                    "Tertiary Description"
                )
            ),
        ) { checkbox, checkedItems ->
        }
    }

    val emphasizedListItem: LiveData<List<ListItemData>> = _emphasizedListItems
    val listItem: LiveData<List<ListItemData>> = _listItems
    val buttonsListItem: LiveData<List<ListItemData>> = _buttonListItem
    val radioListItem: LiveData<ListItemData.RadioGroupListItem> = _radioListItem
    val checkboxItem: LiveData<ListItemData.CheckboxGroupListItem> = _checkboxItem
}