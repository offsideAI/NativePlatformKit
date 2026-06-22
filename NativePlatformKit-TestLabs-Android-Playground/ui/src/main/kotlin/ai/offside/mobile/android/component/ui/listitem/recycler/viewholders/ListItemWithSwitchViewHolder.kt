package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import ai.offside.mobile.android.component.ui.databinding.ListItemWithSwitchLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * List Item View Holder with Switch
 *
 * @param binding
 */
internal class ListItemWithSwitchViewHolder(private val binding: ListItemWithSwitchLayoutBinding) :
    ListItemViewHolder(binding) {
    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        binding.data = data as ListItemData.ListItemWithSwitch
        binding.dividerVisibility = dividerVisibility
    }
}