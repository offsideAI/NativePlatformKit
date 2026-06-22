package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.view.accessibility.AccessibilityNodeInfo.CollectionInfo.SELECTION_MODE_MULTIPLE
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.CheckBoxA11y
import ai.offside.mobile.android.component.ui.databinding.ListItemWithCheckBoxGroupLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithCheckBoxLayoutBinding
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * List Item View Holder for check box group
 *
 * @param binding
 */
internal class ListItemWithCheckboxGroupViewHolder(private val binding: ListItemWithCheckBoxGroupLayoutBinding) :
    ListItemViewHolder(binding) {
    private val checkedButtons = mutableListOf<CompoundButtonData>()

    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        val checkboxData = data as ListItemData.CheckboxGroupListItem
        populateCheckboxes(checkboxData)
        if (checkboxData.items.size > 1) {
            binding.checkBoxGroup.contentDescription = data.label
            setAccessibilityDelegateForCheckBoxGroup(binding.checkBoxGroup, checkboxData.items.size)
        }
    }

    /**
     * Adds checkbox view to the group
     *
     * @param checkboxData
     */
    private fun populateCheckboxes(checkboxData: ListItemData.CheckboxGroupListItem) {
        val context = binding.checkBoxGroup.context
        checkboxData.items.forEachIndexed { index, dataItem ->
            val listItemBinding =
                ListItemWithCheckBoxLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.checkBoxGroup,
                    true
                )
            listItemBinding.checkBox.id = index
            listItemBinding.dataItem = dataItem
            listItemBinding.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                updateCheckedButtons(dataItem, isChecked)
                checkboxData.onCheckBoxChecked(buttonView as CheckBoxA11y, checkedButtons)
            }
            if (index != checkboxData.items.lastIndex) {
                val divider = getDivider(context)
                divider.dividerInsetStart =
                    binding.root.context.resources.getDimension(R.dimen.padding_xxlarge).toInt()
                binding.checkBoxGroup.addView(divider)
            }
            setAccessibilityDelegateForCheckBox(
                listItemBinding.checkBox,
                index
            )
        }
    }

    /**
     * Adds [CompoundButtonData] of checked item to [checkedButtons] list
     *
     * @param itemData
     * @param isChecked Checked status of checkbox
     */
    private fun updateCheckedButtons(itemData: CompoundButtonData, isChecked: Boolean) {
        if (isChecked) {
            if (!checkedButtons.contains(itemData)) {
                checkedButtons.add(itemData)
            }
        } else {
            checkedButtons.remove(itemData)
        }
    }

    /**
     * Sets accessibility delegate for [CheckBoxA11y].
     * Sets [CollectionItemInfoCompat] for accessibility item index announcement
     */
    private fun setAccessibilityDelegateForCheckBox(
        checkBoxA11y: CheckBoxA11y,
        index: Int
    ) {
        ViewCompat.setAccessibilityDelegate(checkBoxA11y, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                /** [CollectionItemInfoCompat] requires rowIndex, rowSpan, columnIndex, columnSpan
                 * and heading as a parameter. These value will help the system announce the indexing
                 * of the checkbox items. In the [ListItem] scenario we do not need column indexing
                 * and hence the default value of index 0 and span 1 is provided */
                info.setCollectionItemInfo(
                    AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(
                        index,
                        1,
                        0,
                        1,
                        false
                    )
                )
            }
        })
    }

    /**
     * Sets accessibility delegate checkbox group [ViewGroup].
     * Sets [CollectionInfoCompat] for accessibility item size announcement
     */
    private fun setAccessibilityDelegateForCheckBoxGroup(checkboxParent: LinearLayout, size: Int) {
        ViewCompat.setAccessibilityDelegate(checkboxParent, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                /** [CollectionInfoCompat] requires rowCount, columnCount, hierarchical and selectionMode.
                 * This would help the system determine the total row and column amount and selection
                 * mode of the group. In [ListItem] scenario we only have 1 column and hence default
                 * value of 1 is provided */
                info.setCollectionInfo(
                    AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(
                        size,
                        1,
                        false,
                        SELECTION_MODE_MULTIPLE
                    )
                )
            }
        })
    }
}