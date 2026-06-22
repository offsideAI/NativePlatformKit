package ai.offside.mobile.android.component.ui.modal.model

import android.view.View
import androidx.annotation.IntRange

/**
 * [CheckboxDataModel] label and description on checkbox modal
 */
data class CheckboxDataModel(
    val checkboxLabel: String = "",
    val checkboxDescription: String = ""
) {

    /**
     * exposes the visibility of checkbox description view
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val checkboxDescriptionVisibility: Int get() = if (checkboxDescription.isNotBlank()) View.VISIBLE else View.GONE
}