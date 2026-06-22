package ai.offside.mobile.android.component.ui.bottomsheet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 *  [BottomSheetViewModel]
 *   Used to manage the bottom sheet click events for selection and dismiss
 */
class BottomSheetViewModel : ViewModel() {

    private val _dismiss: MutableLiveData<Boolean> = MutableLiveData(false)
    val dismiss: LiveData<Boolean> = _dismiss

    val bottomSheetData = MutableLiveData<Any>()

    /**
     *  fun to dismiss the [BottomSheetFlowDialogFragment]
     */
    fun dismiss() = _dismiss.postValue(true)
}