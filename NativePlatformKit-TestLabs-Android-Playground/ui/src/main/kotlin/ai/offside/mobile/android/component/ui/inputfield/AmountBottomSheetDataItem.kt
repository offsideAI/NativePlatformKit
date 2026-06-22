package ai.offside.mobile.android.component.ui.inputfield

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale

@Parcelize
@Keep
data class AmountBottomSheetDataItem(
    val amount: BigDecimal = BigDecimal.ZERO,
    val label: String = "",
) : Parcelable

/**
 * function to get accessibility text
 *
 * @return accessibilityText
 */
fun AmountBottomSheetDataItem.getAccessibilityText(): String {
    val otherString = "Other"
    val accessibleText = if (label.contains(otherString)) {
        label
    } else {
        val amountFormatted = NumberFormat.getCurrencyInstance(Locale.US).format(amount)
        "$amountFormatted, $label"
    }
    return accessibleText
}
