package ai.offside.mobile.android.component.ui.tile.transfer

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters.getCurrencyText
import ai.offside.mobile.android.component.ui.databinding.TransferActivityTileBinding

/**
 * Transfer Tile component
 * @param context
 * @param attributeSet
 */
class TransferTile @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = TransferActivityTileBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates data binding with [TransferActivityTileDataModel]
     * @param tileDataModel
     */
    fun setTransferTileDate(tileDataModel: TransferActivityTileDataModel) {
        binding.transferTileData = tileDataModel
        updateTileContentDescription()
    }

    /**
     * Updates content description for tile with Button role
     */
    private fun updateTileContentDescription() {
        val tileData = binding.transferTileData!!
        var contentDescription = tileData.toAccount.plus(", ${tileData.fromAccount}")
        if(tileData.isRecurring){
            contentDescription = contentDescription.plus(", ${context.getString(R.string.a11y_informative_icon_recurring)}")
        }
        contentDescription = contentDescription.plus(", ${getCurrencyText(tileData.amount)}")
        if(!tileData.status.isNullOrEmpty()) {
            contentDescription = contentDescription.plus(", ${tileData.status}")
        }
        if(!tileData.date.isNullOrEmpty()) {
            contentDescription = contentDescription.plus(", ${tileData.date}")
        }
        binding.transferActivityTileLayout.modifyRoleDescription(contentDescription, Button::class.java.simpleName)
    }

}