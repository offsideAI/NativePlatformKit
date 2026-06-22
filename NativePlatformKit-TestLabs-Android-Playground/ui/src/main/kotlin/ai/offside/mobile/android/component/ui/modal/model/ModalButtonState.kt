package ai.offside.mobile.android.component.ui.modal.model

/**
 * Any modal is mandatory to have one of [ModalButtonState.OneButtonModal], [ModalButtonState.TwoButtonModal], [ModalButtonState.ThreeButtonModal]
 */
sealed interface ModalButtonState {

    val primaryAction: ModalActionData

    /**
     * Modal will show primary button [OneButtonModal]
     */
    data class OneButtonModal(
        override val primaryAction: ModalActionData
    ) : ModalButtonState

    /**
     * Modal will show primary and secondary buttons [TwoButtonModal]
     */
    data class TwoButtonModal(
        override val primaryAction: ModalActionData,
        val secondaryAction: ModalActionData
    ) : ModalButtonState

    /**
     * Modal will show primary, secondary and tertiary buttons [ThreeButtonModal]
     */
    data class ThreeButtonModal(
        override val primaryAction: ModalActionData,
        val secondaryAction: ModalActionData,
        val tertiaryAction: ModalActionData
    ) : ModalButtonState
}
