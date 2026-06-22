package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import ai.offside.mobile.android.component.ui.databinding.SimpleListItemLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * Default List Item view holder class
 *
 * @param binding
 */
internal class SimpleListItemViewHolder(val binding: SimpleListItemLayoutBinding) :
    ListItemViewHolder(binding) {

    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        binding.dividerVisibility = dividerVisibility
        binding.data = data as ListItemData.SimpleListItem
        setButtonRole(data)
    }

    /**
     * Added button role for accessibility
     */
    private fun setButtonRole(data: ListItemData.SimpleListItem) {
        val contentDescriptionBuilder = StringBuilder()
        contentDescriptionBuilder.append(data.label)
        if (data.secondaryText.isNotEmpty()) {
            contentDescriptionBuilder.append(", ${data.secondaryText}")
        }
        setButtonRole(binding.root, contentDescriptionBuilder.toString())
    }
}