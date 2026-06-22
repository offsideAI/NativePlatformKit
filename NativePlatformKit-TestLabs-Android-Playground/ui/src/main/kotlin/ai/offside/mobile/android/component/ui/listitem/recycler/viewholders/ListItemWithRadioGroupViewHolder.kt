package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import android.view.LayoutInflater
import android.widget.RadioButton
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.ListItemWithRadioButtonLayoutBinding
import ai.offside.mobile.android.component.ui.databinding.ListItemWithRadioGroupLayoutBinding
import ai.offside.mobile.android.component.ui.compoundbuttondata.CompoundButtonData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * List Item View Holder with Radio Button Group
 *
 * @param binding
 */
internal class ListItemWithRadioGroupViewHolder(private val binding: ListItemWithRadioGroupLayoutBinding) :
    ListItemViewHolder(binding) {
    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        val radioGroupData = data as ListItemData.RadioGroupListItem
        populateRadioButtons(radioGroupData.items)
        binding.radioGroup.contentDescription = data.label
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val button = binding.radioGroup.findViewById<RadioButton>(checkedId)
            radioGroupData.onRadioButtonChecked(group, button.id)
        }
    }

    /**
     * Adds RadioButton to the RadioGroup
     *
     * @param items
     */
    private fun populateRadioButtons(items: List<CompoundButtonData>) {
        val context = binding.radioGroup.context
        items.forEachIndexed { index, dataItem ->
            val listItemBinding =
                ListItemWithRadioButtonLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.radioGroup,
                    true
                )
            listItemBinding.radioButton.id = index
            listItemBinding.dataItem = dataItem
            if (index != items.lastIndex) {
                val divider = getDivider(context)
                divider.dividerInsetStart = binding.root.context.resources.getDimension(R.dimen.padding_xxlarge).toInt()
                binding.radioGroup.addView(divider)
            }
        }
    }
}