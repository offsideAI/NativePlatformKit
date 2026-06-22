package ai.offside.mobile.android.component.ui.tile.account

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.TouchDelegate
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.AccountTileLayoutBinding
import ai.offside.mobile.android.component.ui.extensions.addSpaceBetweenCharacter
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileData
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileDataModel
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTilePaymentStatus.*
import kotlin.math.roundToInt

/**
 * Account tile layout component class
 *
 * @param context
 * @param attributeSet
 */
class AccountTileLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {
    private val binding =
        AccountTileLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Increases the touchable size for more menu button
     */
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setTouchDelegateForView(
            binding.moreMenu,
            resources.getDimension(R.dimen.padding_medium).roundToInt()
        )
    }


    /**
     * Updates data binding with [AccountTileData]
     *
     * @param accountTileData
     */
    fun setAccountTileData(accountTileData: AccountTileDataModel) {
        binding.tileData = accountTileData
        setAdditionalViewData(accountTileData.tileData)
        setAccountTitleContentDescription()
    }

    /**
     * Update view binding data based on [AccountTile] data types
     *
     * @param accountTile
     */
    private fun setAdditionalViewData(accountTile: AccountTileData) {
        if (accountTile is AccountTileData.AccountTileWidgetMoreInfoButton) {
            binding.moreMenu.setOnClickListener { v ->
                accountTile.moreButton.onButtonClick(v)
            }
        }

        if (accountTile is AccountTileData.AccountTileWidgetPrimaryMicroButton) {
            binding.primaryButton.text = accountTile.primaryButtonData.label
            binding.primaryButton.setOnClickListener { v ->
                accountTile.primaryButtonData.onButtonClick(v)
            }
        }

        if (accountTile is AccountTileData.AccountTileWidgetAlternateMicroButton) {
            binding.alternateButton.text = accountTile.alternateButton.label
            binding.alternateButton.setOnClickListener { v ->
                accountTile.alternateButton.onButtonClick(v)
            }
        }

        if (accountTile is AccountTileData.AccountTileWidgetButtonHint) {
            binding.buttonHint.text = accountTile.hintText
        }

        if (accountTile is AccountTileData.AccountTileWidgetPaymentDue) {
            binding.paymentDueInfo.data = accountTile
        }

        if (accountTile is AccountTileData.AccountTileWidgetPricingUpdate) {
            binding.pricingUpdatesInfo.data = accountTile
        }

        if (accountTile is AccountTileData.AccountTileWidgetRateChange) {
            binding.rateChange.data = accountTile
        }

        if (accountTile is AccountTileData.AccountTileWidgetLabelValuePair) {
            binding.labelValuePairFirst.data = accountTile.labelValuePair
        }

        if (accountTile is AccountTileData.AccountTileWidgetTwoLabelValuePair) {
            binding.labelValuePairSecond.data = accountTile.secondLabelValuePair
        }
    }

    /**
     * Increases the touch area of a view
     *
     * @param view Target [View]
     * @param additionalSpace [Int]
     */
    private fun setTouchDelegateForView(view: View, additionalSpace: Int) {
        val parent = view.parent as View
        parent.post {
            val touchableArea = Rect()
            view.getHitRect(touchableArea)
            touchableArea.top -= additionalSpace
            touchableArea.bottom += additionalSpace
            touchableArea.left -= additionalSpace
            touchableArea.right += additionalSpace
            parent.touchDelegate = TouchDelegate(touchableArea, view)
        }
    }

    /**
     * Set custom content description to the title view
     */
    private fun setAccountTitleContentDescription() {
        val formattedMaskedAccountNumber =
            binding.tileData?.maskedAccountNumber?.replace("x", "")?.addSpaceBetweenCharacter()
        val customContentDescription = "${binding.tileData?.accountName} ${
            context.getString(
                R.string.account_tile_ending_in_des,
                formattedMaskedAccountNumber
            )
        }"
        binding.displayName.contentDescription = customContentDescription
    }
}