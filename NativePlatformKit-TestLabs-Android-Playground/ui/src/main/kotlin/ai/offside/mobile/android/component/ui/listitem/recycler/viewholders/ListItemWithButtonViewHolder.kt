package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import com.google.android.material.button.MaterialButton
import ai.offside.mobile.android.component.ui.databinding.ListItemWithButtonLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemButtonType
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * List Item view holder with button
 */
class ListItemWithButtonViewHolder(
    private val binding: ListItemWithButtonLayoutBinding
) : ListItemViewHolder(binding) {
    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        binding.data = data as ListItemData.ListItemWithButton
        binding.dividerVisibility = dividerVisibility

        val hasIcon = data.iconRes != ResourcesCompat.ID_NULL
        val activeButton: MaterialButton = when (data.buttonType) {
            ListItemButtonType.PRIMARY -> binding.primaryButton
            ListItemButtonType.ALTERNATE -> binding.alternateButton
        }
        activeButton.isVisible = true
        if (hasIcon) {
            activeButton.setIconResource(data.iconRes)
        }
    }
}