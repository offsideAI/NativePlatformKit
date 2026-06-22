package ai.offside.mobile.android.component.ui.tile.accounttransactions

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters.getCurrencyText
import ai.offside.mobile.android.component.ui.databinding.AccountTransactionsTileBinding

/**
 * Account Transaction Tile component
 * @param context
 * @param attributeSet
 */
class AccountTransactionTile @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = AccountTransactionsTileBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates data binding with [AccountTransactionsTileDataModel]
     * @param tileDataModel
     */
    fun setTransactionTileData(tileDataModel: AccountTransactionsTileDataModel) {
        binding.tileData = tileDataModel
        updateTileContentDescription()
    }

    /**
     * Updates content description for tile with Button role
     */
    private fun updateTileContentDescription() {
        val tileData = binding.tileData!!
        var contentDescription = tileData.description.plus(", ${getCurrencyText(tileData.amount)}")

        if(!tileData.category.isNullOrEmpty()) {
            contentDescription = contentDescription.plus(", ${tileData.category}")
        }
        if(tileData.runningBalance != null) {
            contentDescription = contentDescription.plus(", ${getCurrencyText(tileData.runningBalance)}")
        }

        binding.transactionTileLayout.modifyRoleDescription(contentDescription, Button::class.java.simpleName)
    }
}