package ai.offside.mobile.android.component.ui.modal.model

import androidx.annotation.DrawableRes

sealed interface ModalDataState {
    val title: String
    val bodyContent: List<String>
    val actionData: ModalButtonState

    /**
     * Modal will be presented as
     *  Title
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  Actions (Buttons)
     */
    data class SimpleModal(
        override val title: String,
        override val bodyContent: List<String>,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  Title
     *  Sub-Header (Bold text above the body)
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  Actions (Buttons)
     */
    data class SimpleSubHeaderModal(
        override val title: String,
        val subHeader: String,
        override val bodyContent: List<String>,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  iCon
     *  Title
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  Actions (Buttons)
     */
    data class MediumModal(
        @DrawableRes val icon: Int,
        override val title: String,
        override val bodyContent: List<String>,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  Title
     *  Supplementary text (below header title)
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  Actions (Buttons)
     */
    data class LargeModal(
        @DrawableRes val icon: Int,
        override val title: String,
        val supplementary: String,
        override val bodyContent: List<String>,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  iCon
     *  Title
     *  Sub-Header (Bold text above the body)
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  Actions (Buttons)
     */
    data class LargeSubHeaderModal(
        @DrawableRes val icon: Int,
        override val title: String,
        val subHeader: String,
        override val bodyContent: List<String>,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  Title
     *  Sub-Header (Bold text above the body)
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  CheckBox and the label along with description on next line if available
     *  Actions (Buttons)
     */
    data class SmallCheckboxModal(
        override val title: String,
        val subHeader: String,
        override val bodyContent: List<String>,
        val checkboxData: CheckboxDataModel,
        override val actionData: ModalButtonState
    ): ModalDataState

    /**
     * Modal will be presented as
     *  iCon
     *  Title
     *  Sub-Header (Bold text above the body)
     *  Body content (array of paragraph content), this will enable the accessibility to read individually on each swipe
     *  CheckBox and the label along with description on next line if available
     *  Actions (Buttons)
     */
    data class MediumCheckboxModal(
        @DrawableRes val icon: Int,
        override val title: String,
        override val bodyContent: List<String>,
        val checkboxData: CheckboxDataModel,
        override val actionData: ModalButtonState
    ): ModalDataState
}