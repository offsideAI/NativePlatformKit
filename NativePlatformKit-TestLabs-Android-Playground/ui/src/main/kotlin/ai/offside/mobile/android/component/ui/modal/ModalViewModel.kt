package ai.offside.mobile.android.component.ui.modal

import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ai.offside.mobile.android.component.ui.modal.model.CheckboxDataModel
import ai.offside.mobile.android.component.ui.modal.model.ModalDataModel
import ai.offside.mobile.android.component.ui.modal.model.ModalDataState

/**
 * ModalViewModel primarily used to manipulate the data into Data-Binding consumable
 */
class ModalViewModel : ViewModel() {

    private val _modalData: MutableLiveData<ModalDataModel> = MutableLiveData()
    val modalData: LiveData<ModalDataModel> get() = _modalData

    /**
     * @param ModalDataState The modal that user wants to see on the screen.
     *      The data are manipulated and assigned to individual variables which will be used in the Data-Binding
     */
    fun setModalData(modalData: ModalDataState) {
        var modalSubHeader = ""
        var modalSupplementary = ""
        @DrawableRes var modalIcon: Int = 0
        var checkboxModal = CheckboxDataModel()
        when(modalData) {
            is ModalDataState.SimpleSubHeaderModal -> {
                modalSubHeader = modalData.subHeader
            }
            is ModalDataState.MediumModal -> {
                modalIcon = modalData.icon
            }
            is ModalDataState.LargeModal -> {
                modalIcon = modalData.icon
                modalSupplementary = modalData.supplementary
            }
            is ModalDataState.LargeSubHeaderModal -> {
                modalIcon = modalData.icon
                modalSubHeader = modalData.subHeader
            }
            is ModalDataState.SmallCheckboxModal -> {
                modalSubHeader = modalData.subHeader
                checkboxModal = modalData.checkboxData
            }
            is ModalDataState.MediumCheckboxModal -> {
                modalIcon = modalData.icon
                checkboxModal = modalData.checkboxData
            }
            else -> Unit
        }
        _modalData.postValue(
            ModalDataModel(
                icon = modalIcon,
                modalTitle = modalData.title,
                modalSubHeader = modalSubHeader,
                modalSupplementary = modalSupplementary,
                modalBody = modalData.bodyContent,
                buttonState = modalData.actionData,
                checkboxDataModel = checkboxModal
            )
        )
    }
}

