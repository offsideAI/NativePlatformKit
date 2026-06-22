package ai.offside.mobile.android.component.ui.tile.online_payments

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.bindingadapters.MaterialTextViewBindingAdapters.getCurrencyText
import ai.offside.mobile.android.component.ui.databinding.OnlinePaymentsTileLayoutBinding
import ai.offside.mobile.android.component.ui.tile.online_payments.OnlinePaymentsTileDataModel.OnlinePaymentsType.*

/**
 * OnlinePayments tile layout component class
 *
 * @param context
 * @param attributeSet
 */
class OnlinePaymentsTile @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null) :
    ConstraintLayout(context, attributeSet) {

    private val binding = OnlinePaymentsTileLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates data binding with [OnlinePaymentsTileData]
     *
     * @param tileDataModel
     */
    fun setOnlinePaymentsTileData(tileDataModel: OnlinePaymentsTileDataModel) {
        binding.model = tileDataModel
        updateOnlinePaymentsDataUI(tileDataModel.onlinePaymentsTileData)
        updateTileContentDescription(tileDataModel)
    }

    /**
     * Update image view with static image res and image url
     * Change the text appearance based on online_payments types
     *
     * @param onlinePaymentsTileData
     */
    private fun updateOnlinePaymentsDataUI(onlinePaymentsTileData: OnlinePaymentsTileData) {
        if (onlinePaymentsTileData.onlinePaymentsImageUrl.isNotEmpty()) {
            binding.onlinePaymentsTileImage.apply {
                loadImageUrl(onlinePaymentsTileData.onlinePaymentsImageUrl)
                background = context.getDrawable(R.drawable.transparent_background)
            }
        }
        when (onlinePaymentsTileData.onlinePaymentsType) {
            ONLINE_PAYMENTS_EBILL_LATE, ONLINE_PAYMENTS_REMINDER_LATE, ONLINE_PAYMENTS_REQUEST_LATE -> binding.onlinePaymentsPaymentDate.apply {
                setTextAppearance(R.style.Offside_Redesign_OnlinePayments_SubText_Error)
            }

            else -> {}
        }
    }

    /**
     * Add button role with content description for tile
     *
     * @param tileDataModel
     */
    private fun updateTileContentDescription(tileDataModel: OnlinePaymentsTileDataModel) {
        val tileData = tileDataModel.onlinePaymentsTileData!!
        var contentDescription = tileData.onlinePaymentsDisplayName
        if(tileDataModel.editButtonVisibility == VISIBLE) {
            contentDescription = contentDescription.plus(", ${context.getString(R.string.a11y_informative_icon_recurring)}")
        }
        contentDescription = contentDescription.plus(", ${getCurrencyText(tileData.onlinePaymentsAmount)}")
        if(tileDataModel.fromAccountViewVisibility == VISIBLE) {
            contentDescription = contentDescription.plus(", ${tileData.onlinePaymentsFromAccount}")
        }
        contentDescription = contentDescription.plus(", ${tileData.onlinePaymentsDueDate}")
        if(tileDataModel.onlinePaymentsTypeVisibility == VISIBLE) {
            contentDescription = contentDescription.plus(", ${tileData.onlinePaymentsType.type}")
        }
        binding.onlinePaymentsTileContainer.modifyRoleDescription(contentDescription, Button::class.java.simpleName)
    }
}