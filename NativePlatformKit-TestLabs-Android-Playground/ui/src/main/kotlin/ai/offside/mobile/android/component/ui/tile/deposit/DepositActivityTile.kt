package ai.offside.mobile.android.component.ui.tile.deposit

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.annotation.ColorRes
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.TileDepositActivityLayoutBinding
import ai.offside.mobile.android.component.ui.extensions.addSpaceBetweenCharacter


/**
 * Deposit Card Tile, contains primary and secondary text content and image
 * @param context
 * @param attributeSet
 */
class DepositActivityTile @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding =
        TileDepositActivityLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates data binding with [DepositActivityTileData]
     * @param depositActivityTileDataModel
     */
    fun setDepositTileData(depositActivityTileDataModel: DepositActivityTileDataModel<*>) {
        binding.model = depositActivityTileDataModel
        updateTileContentDescription()
    }


    /**
     * Updates tile ending textColor
     * @param textColor color res
     */
    fun setPrimaryAmountTextColor(@ColorRes textColor: Int) {
        binding.depositActivityTileAmountPrimaryText.setTextColor(
            ColorStateList.valueOf(
                resources.getColor(
                    textColor,
                    null
                )
            )
        )
    }

    /**
     * get content description for the deposit tile.
     */
    private fun getProductDescription(): String{
        val productDescription = binding.model?.primaryTextRow?.starting
        if (productDescription?.contains("[0-9]".toRegex())!!) {
            val primaryInfoList = productDescription.split('x', ignoreCase = true)
            return "${primaryInfoList[0]} ending in ${primaryInfoList[1].addSpaceBetweenCharacter()}"
        }
        return productDescription
    }

    /**
     * Update tile content description with role as button for A11Y support.
     */
    private fun updateTileContentDescription() {
        val tileDataModel = binding.model!!
        var contentDescription = getProductDescription().plus(", ${tileDataModel.primaryTextRow.ending}")
        if(tileDataModel.secondaryTextRow.startingEnabled) {
            contentDescription = contentDescription.plus(", ${tileDataModel.secondaryTextRow.starting}")
        }
        if(tileDataModel.secondaryTextRow.endingEnabled) {
            contentDescription = contentDescription.plus(", ${tileDataModel.secondaryTextRow.ending}")
        }

        binding.depositActivityTileConstraintLayout.modifyRoleDescription(contentDescription, Button::class.java.simpleName)
    }

}

