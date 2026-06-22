package ai.offside.mobile.android.component.ui.inputfield

import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.databinding.BindingAdapter
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.RadioButtonA11y


/**
 * Set accessibility delegate to viewGroup consisting of radio-button and using data [AmountBottomSheetDataItem]
 *
 * @param radioButtonA11y ref of [RadioButtonA11y]
 */
@BindingAdapter("amountOptionAccessibilityDelegate")
fun ViewGroup.setAmountOptionAccessibilityDelegate(
    radioButtonA11y: RadioButtonA11y
) {

    if (radioButtonA11y.tag !is AmountBottomSheetDataItem) {
        return
    }

    val data = radioButtonA11y.tag as AmountBottomSheetDataItem
    val accessibleText = data.getAccessibilityText()
    this.setOnClickListener {
        radioButtonA11y.toggle()
    }

    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                // Add selected / not selected state of radio button in a11y announcement
                val selectedState = if (radioButtonA11y.isChecked)
                    context.getString(R.string.a11y_state_selected)
                else
                    context.getString(R.string.a11y_state_not_selected)

                val contentDescription = String.format(
                    context.getString(R.string.a11y_content_description_two_str),
                    accessibleText,
                    selectedState
                )

                info.apply {
                    stateDescription = contentDescription
                    className = RadioButton::class.qualifiedName
                }
            }
        })
}