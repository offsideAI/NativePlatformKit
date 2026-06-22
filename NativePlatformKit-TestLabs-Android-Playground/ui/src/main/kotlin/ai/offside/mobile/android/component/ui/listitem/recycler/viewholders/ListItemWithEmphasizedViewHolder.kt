package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.EmphasizedListItemLayoutBinding
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData
import ai.offside.mobile.android.component.ui.listitem.data.ListItemIconTint

/**
 * List Item View Holder with Emphasized Icon
 *
 * @param binding
 */
internal class ListItemWithEmphasizedViewHolder(val binding: EmphasizedListItemLayoutBinding) :
    ListItemViewHolder(binding) {
    override fun bind(data: ListItemData, dividerVisibility: Int) {
        setEnabled(data.enabled)
        binding.data = data as ListItemData.ListItemWithEmphasizedIcon
        binding.dividerVisibility = dividerVisibility

        when (data.iconTint) {
            ListItemIconTint.PRIMARY -> {
                binding.icon.setTint(
                    ColorStateList.valueOf(
                        MaterialColors.getColor(
                            binding.root.context,
                            R.attr.offside_primary,
                            ContextCompat.getColor(binding.root.context, R.color.sys_color_blue40)
                        )
                    )
                )
            }
            else -> Unit
        }
    }
}