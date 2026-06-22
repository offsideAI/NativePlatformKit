package ai.offside.mobile.android.component.ui.listactions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.ListActionsLayoutBinding

/**
 * List action Tile component
 * @param context
 * @param attributeSet
 */
class ListActionTile @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = ListActionsLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates data binding with [ListActionsDataModel]
     * Add content description for tile with Button role
     * @param tileDataModel
     */
    fun setActionTileData(tileData: ListActionsDataModel) {
        binding.listActionsData = tileData
        binding.listActionsContainer.modifyRoleDescription(binding.listActionsData!!.actionText, Button::class.java.simpleName)
    }

}