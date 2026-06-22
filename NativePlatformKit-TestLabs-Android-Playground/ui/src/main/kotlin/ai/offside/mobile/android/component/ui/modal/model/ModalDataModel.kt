package ai.offside.mobile.android.component.ui.modal.model

import android.view.View
import androidx.annotation.DrawableRes
import androidx.annotation.IntRange

/**
 * Data used to represent the View
 */
data class ModalDataModel(
    @DrawableRes val icon: Int,
    val modalTitle: String,
    val modalSubHeader: String = "",
    val modalSupplementary: String = "",
    val modalBody: List<String>,
    val buttonState: ModalButtonState,
    val checkboxDataModel: CheckboxDataModel
) {

    /**
     * expose the visibility of the modal icon [ModalDataModel.icon]
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val modalIconVisibility: Int get() = if (this.icon != 0) View.VISIBLE else View.GONE

    /**
     * Exposes the sub header visibility [ModalDataModel.modalSubHeader]
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val modalSubHeaderVisibility: Int get() = if (this.modalSubHeader.isNotBlank()) View.VISIBLE else View.GONE

    /**
     * Exposes the supplementary visibility [ModalDataModel.modalSupplementary]
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val modalSupplementaryVisibility: Int get() = if (this.modalSupplementary.isNotBlank()) View.VISIBLE else View.GONE

    /**
     * Exposes the checkbox container visibility [ModalDataModel.checkboxDataModel]
     */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val checkboxModalVisibility: Int get() = if (checkboxDataModel.checkboxLabel.isNotBlank()) View.VISIBLE else View.GONE
}