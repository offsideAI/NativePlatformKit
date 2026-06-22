package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import ai.offside.mobile.android.component.ui.databinding.ListItemWithStatusLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * List Item View Holder with Status
 *
 * @param binding
 */
internal class ListItemWithStatusViewHolder(val binding: ListItemWithStatusLayoutBinding) :
    ListItemViewHolder(binding) {
    override fun bind(data: ListItemData, dividerVisibility : Int) {
        setEnabled(data.enabled)
        binding.dividerVisibility = dividerVisibility
        binding.data = data as ListItemData.ListItemWithStatus
        setButtonRole(data)
    }

    /**
     * Added button role for accessibility
     */
    private fun setButtonRole(data: ListItemData.ListItemWithStatus) {
        val contentDescriptionBuilder = StringBuilder()
        contentDescriptionBuilder.append(data.label)
        if (data.secondaryText.isNotEmpty()) {
            contentDescriptionBuilder.append(", ${data.secondaryText}")
        }
        contentDescriptionBuilder.append(", ${data.status}")
        setButtonRole(binding.root, contentDescriptionBuilder.toString())
    }
}