package ai.offside.mobile.android.component.ui.dropdown

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.databinding.DropdownLayoutBinding
import ai.offside.mobile.android.component.ui.inputfield.setDropdownAccessibilityText

/**
 * Dropdown layout component class
 * @param context
 * @param attributeSet
 */
class Dropdown @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    private val binding = DropdownLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Set Dropdown Data
     * @param data DropdownDataModel
     */
    fun setDropdownData(data: DropdownDataModel) {
        binding.model = data
        setAccessibilityDescription()
    }

    /**
     * Set Accessibility Description
     */
    private fun setAccessibilityDescription() {
        binding.dropdownTextLayout.apply {
            val defaultAccessibilityText =
                "${binding.model?.label} ${binding.model?.primaryInfoAccessibilityDescription} ${binding.model?.selectedItem?.secondaryDescription} ${binding.model?.selectedItem?.tertiaryDescription}"
            setDropdownAccessibilityText(defaultAccessibilityText)
        }
    }

    /**
     * Enable/disable dropdown - enabled by default
     * @param isEnable
     */
    fun setDropdownEnabled(isEnabled: Boolean) {
        binding.dropdownTextLayout.isEnabled = isEnabled
    }
}
