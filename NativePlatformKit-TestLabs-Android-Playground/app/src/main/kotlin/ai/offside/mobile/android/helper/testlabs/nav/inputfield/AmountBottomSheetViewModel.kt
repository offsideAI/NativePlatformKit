package ai.offside.mobile.android.helper.testlabs.nav.inputfield

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ai.offside.mobile.android.component.ui.inputfield.AmountBottomSheetDataItem
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

/**
 * Amount bottom sheet view model
 *
 */
class AmountBottomSheetViewModel : ViewModel() {

    private val amountDetailsData = MutableLiveData<List<AmountBottomSheetDataItem>>()

    init {
        getAmountDetails()
    }

    val amountDetailsLiveData: LiveData<List<AmountBottomSheetDataItem>> = amountDetailsData

    /**
     * Function to get amount details data.
     *
     */
    private fun getAmountDetails() {
        val amountList = mutableListOf(
            AmountBottomSheetDataItem(
                BigDecimal(1250.45).setScale(2, RoundingMode.FLOOR), "Current Balance"
            ), AmountBottomSheetDataItem(
                BigDecimal(75.65).setScale(2, RoundingMode.FLOOR), "Minimum Payment"
            ), AmountBottomSheetDataItem(
                BigDecimal(750).setScale(2, RoundingMode.FLOOR), "Last Posted Payment"
            ), AmountBottomSheetDataItem(label = "Other Amount")
        )
        amountDetailsData.value = amountList
    }

    /**
     * Function to format amount with currency $ sign
     *
     * @param amount input amount in [BigDecimal]
     * @return formatted amount in [String]
     */
    fun getAmountFormatted(amount: BigDecimal): String {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount)
    }
}