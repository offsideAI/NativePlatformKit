package ai.offside.mobile.android.component.ui.modal.model

import android.view.View

/**
 * [ModalActionData] label and the click action to be performed
 */
data class ModalActionData(
    val label: String = "",
    val buttonType: ModalButtonType = ModalButtonType.PRIMARY,
    val onActionListener: ((View) -> Unit)
)
