package ai.offside.mobile.android.component.ui.inputfield

import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.ViewCompat.ACCESSIBILITY_LIVE_REGION_NONE
import androidx.core.view.ViewCompat.ACCESSIBILITY_LIVE_REGION_POLITE
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.google.android.material.textview.MaterialTextView
import ai.offside.mobile.android.component.ui.R


/**
 * Function to set custom error message to [TextInputLayout].
 * Use this function when TextInputLayout consists of below view hierarchy:
 * <TextInputLayout>
 *     <TextInputEditText/>
 *         <MaterialTextView/> -> Textview to show custom error message
 *             <MaterialDivider/> -> Divider to show custom box-stroke
 *  </TextInputLayout>
 * @param errorTextView text-view to show custom error message
 * @param errorMessage error-message
 */
fun TextInputLayout.setCustomErrorMessage(errorTextView: MaterialTextView, errorMessage: String) {

    // Set box stroke width to show red divider line during setError() call
    val width = context?.resources?.getDimensionPixelSize(R.dimen.text_input_layout_stroke_width) ?: 0
    boxStrokeWidth = width
    boxStrokeWidthFocused = width

    // Set empty (with space) error message to highlight input layout box as error
    error = " "
    // Set isErrorEnabled to true for A11Y error announcement
    isErrorEnabled = true

    // Own custom error message
    errorTextView.apply {
        visibility = View.VISIBLE
        text = errorMessage
    }
    errorContentDescription = errorMessage
}

/**
 * Function to remove custom error message from [TextInputLayout].
 * Use this function when TextInputLayout consists of below view hierarchy:
 *  <TextInputLayout>
 *      <TextInputEditText/>
 *          <MaterialTextView/> -> Textview to show custom error message
 *              <MaterialDivider/> -> Divider to show custom box-stroke
 *  </TextInputLayout>
 * @param errorTextView error message text-view.
 */
fun TextInputLayout.removeCustomErrorMessage(errorTextView: MaterialTextView) {

    // Remove error and regain input layout box style to original set design via style
    error = null
    // Reset isErrorEnabled to false to remove A11Y error announcement
    isErrorEnabled = false
    errorContentDescription = ""

    // Reset input layout box stroke width to zero to have our own divider - MaterialDivider in effect
    boxStrokeWidth = 0
    boxStrokeWidthFocused = 0

    // Remove custom error message
    errorTextView.apply {
        visibility = View.GONE
    }
}

/**
 * Function to set accessibility delegate to [TextInputLayout] for error amount input.
 *
 * @param inputEditText Associated inputEditText [TextInputEditText] for TextInputLayout to get editable amount value.
 * @param errorTextView Text-view reference of custom-error message when inputEditText value is invalid.
 */
fun TextInputLayout.setAccessibilityDelegateForErrorAmountInput(
    inputEditText: TextInputEditText,
    errorTextView: MaterialTextView
) {
    setTextInputAccessibilityDelegate(object : TextInputLayout.AccessibilityDelegate(this) {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (isErrorEnabled) {
                val inputText = if (inputEditText.editableText.isNullOrEmpty()) {
                    hint
                } else {
                    inputEditText.editableText
                }
                info.text = context.getString(
                    R.string.input_field_error_content_description, inputText, errorTextView.text
                )
                errorAccessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_POLITE
            }
        }
    })
}

/**
 * Function to set accessibility delegate to for amount input field to announce asterisk(*).
 *
 * @param inputEditText Associated inputEditText amount input field.
 * @receiver [TestUIRedesignAmountInputFieldFragment]
 */
fun TextInputLayout.setAccessibilityDelegateForAmountInput(inputEditText: TextInputEditText){
    ViewCompat.setAccessibilityDelegate(inputEditText, object : AccessibilityDelegateCompat(){
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.stateDescription = context.getString(R.string.a11y_amount_label_content_description)
                    .plus(context.getString(R.string.a11y_amount_asterisk_content_description))
            info.className = EditText::class.java.simpleName
            info.roleDescription = EditText::class.java.simpleName
        }
    })
}


/**
 * Function to set accessibility text to [TextInputLayout] as drop-down.
 * Drop-down component has role of button.
 * @param accessibilityText - accessibility text
 */
fun TextInputLayout.setDropdownAccessibilityText(accessibilityText: String) {

    importantForAccessibility = View.IMPORTANT_FOR_ACCESSIBILITY_YES

    ViewCompat.setAccessibilityDelegate(
        this,
        object : AccessibilityDelegateCompat() {

            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)

                info.apply {
                    stateDescription = accessibilityText
                    contentDescription = accessibilityText
                    className = Button::class.qualifiedName
                }
            }
        })
}

/**
 * Function to get accessibility text for two-line drop-Down component
 *
 * @param dropDownLabel drop-down component label
 * @param selectedPrimaryInfo primary info of selected drop-down item
 * @param selectedSecondaryInfo secondary info of selected drop-down item
 */
fun TextInputLayout.getTwoLineDropDownAccessibilityText(
    dropDownLabel: String,
    selectedPrimaryInfo: String,
    selectedSecondaryInfo: String
) = String.format(
    context.getString(R.string.a11y_two_line_dropdown_content_des),
    dropDownLabel,
    selectedPrimaryInfo,
    selectedSecondaryInfo
)

/**
 * Function to set accessibility delegate to [TextInputLayout]
 * sets clear end icon content description and handles click listener
 *
 * @param inputEditText [TextInputEditText]
 */
fun TextInputLayout.setAccessibilityDelegateForTextInputLayout(inputEditText: TextInputEditText) {
    setTextInputAccessibilityDelegate(object : TextInputLayout.AccessibilityDelegate(this) {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            endIconContentDescription = context.getString(R.string.input_field_clear_content_description, inputEditText.hint)
            setEndIconOnClickListener {
                endIconDrawable.apply {
                    inputEditText.editableText.clear()
                    performAccessibilityAction(inputEditText, AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)
                }
            }
        }
    })
}

/**
 * Function to set accessibility delegate to [TextInputLayout] character count
 * sets character count in content description along with input text
 * sets errorAccessibilityLiveRegion to NONE to fix duplicate announcement for error message
 * sets clear end icon content description and handles click listener
 *
 * @param inputEditText Associated inputEditText for TextInputLayout to get editable text
 */
fun TextInputLayout.setAccessibilityDelegateForCharCountTextInput(inputEditText: TextInputEditText) {
    findViewById<AppCompatTextView>(com.google.android.material.R.id.textinput_counter).importantForAccessibility =
        View.IMPORTANT_FOR_ACCESSIBILITY_NO
    setTextInputAccessibilityDelegate(object : TextInputLayout.AccessibilityDelegate(this) {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            val inputText = if (inputEditText.editableText.isNullOrEmpty()) {
                hint
            } else {
                inputEditText.editableText
            }
            info.text = context.getString(
                    R.string.input_field_char_count_content_description,
                    inputText,
                    inputEditText.editableText.count(),
                    counterMaxLength
                )
            errorAccessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_NONE
            endIconContentDescription = context.getString(R.string.input_field_clear_content_description, inputEditText.hint)
            setEndIconOnClickListener {
                endIconDrawable.apply {
                    inputEditText.editableText.clear()
                    performAccessibilityAction(inputEditText, AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)
                }
            }
        }
    })
}

/**
 * Function to set accessibility delegate to [TextInputLayout] error text
 * If error is enabled, set custom content description and
 * sets errorAccessibilityLiveRegion to NONE to fix duplicate announcement for error message
 * sets clear end icon content description and handles click listener
 *
 * @param inputEditText Associated inputEditText for TextInputLayout to get editable text
 */
 fun TextInputLayout.setAccessibilityDelegateForErrorTextInput(inputEditText: TextInputEditText) {
    setTextInputAccessibilityDelegate(object : TextInputLayout.AccessibilityDelegate(this) {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            if (isErrorEnabled) {
                val inputText = if (inputEditText.editableText.isNullOrEmpty()) {
                    hint
                } else {
                    inputEditText.editableText
                }
                info.text = context.getString(
                    R.string.input_field_error_content_description,
                    inputText,
                    error
                )
                errorAccessibilityLiveRegion = ACCESSIBILITY_LIVE_REGION_NONE
            }

            endIconContentDescription = context.getString(R.string.input_field_clear_content_description, inputEditText.hint)
            setEndIconOnClickListener {
                endIconDrawable.apply {
                    inputEditText.editableText.clear()
                    performAccessibilityAction(inputEditText, AccessibilityNodeInfo.ACTION_ACCESSIBILITY_FOCUS, null)
                }
            }
        }
    })
}