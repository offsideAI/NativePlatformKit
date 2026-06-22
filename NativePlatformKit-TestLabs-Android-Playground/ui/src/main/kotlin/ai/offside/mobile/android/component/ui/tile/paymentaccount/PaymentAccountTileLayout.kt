package ai.offside.mobile.android.component.ui.tile.paymentaccount

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.card.MaterialCardView
import ai.offside.mobile.android.component.ui.databinding.PaymentAccountTileLayoutBinding
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileData
import ai.offside.mobile.android.component.ui.tile.paymentaccount.data.PaymentAccountTileDataModel

/**
 * Payment Account Tile Component class
 *
 * @param context
 * @param attributeSet
 */
class PaymentAccountTileLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewElevatedStyle,
    attachToParent: Boolean = true
) : MaterialCardView(context, attributeSet, defaultStyleAttr) {

    private val binding =
        PaymentAccountTileLayoutBinding.inflate(LayoutInflater.from(context), this, attachToParent)

    val root get() = binding.root

    fun setAccountData(tileData: PaymentAccountTileDataModel) {
        binding.data = tileData
        updateAccessibilityDescription(tileData)
    }

    /**
     * Updates content description of header and payment date tiles
     *
     * @param data [PaymentAccountTileData]
     */
    private fun updateAccessibilityDescription(data: PaymentAccountTileData) {
        binding.header.root.contentDescription =
            "${data.displayName}, ${data.accountType}, ${data.viewAccount.label}"
        addButtonRole(binding.header.root)
        binding.header.root.setOnClickListener { v ->
            data.viewAccount.onButtonClick(v)
        }
    }

    /**
     * Adds accessibility button role
     */
    private fun addButtonRole(view: View) {
        ViewCompat.setAccessibilityDelegate(view, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.roleDescription = binding.primaryButton.javaClass.name
            }
        })
    }
}