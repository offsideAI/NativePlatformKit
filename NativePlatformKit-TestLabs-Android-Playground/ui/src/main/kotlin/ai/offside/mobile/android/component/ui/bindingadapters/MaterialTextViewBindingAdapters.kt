package ai.offside.mobile.android.component.ui.bindingadapters

import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.textview.MaterialTextView
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

object MaterialTextViewBindingAdapters {
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    private val currency: Currency = Currency.getInstance("USD")

    fun getCurrencyText(amount: BigDecimal, showPositiveSymbol: Boolean = false): String {
        currencyFormat.currency = currency
        var stringAmount = currencyFormat.format(amount)
        if (showPositiveSymbol && amount > BigDecimal.ZERO) {
            stringAmount = "+$stringAmount"
        }
        return stringAmount
    }

    @JvmStatic
    @BindingAdapter("amountText")
    fun MaterialTextView.setAmountText(amount: BigDecimal?) {
        text = getCurrencyText(amount ?: BigDecimal.ZERO)
    }

    @JvmStatic
    @BindingAdapter("amountTextWithChange")
    fun MaterialTextView.setAmountTextWithChange(amount: BigDecimal?) {
        text = getCurrencyText(amount ?: BigDecimal.ZERO, true)
    }

    @JvmStatic
    @BindingAdapter("optionalText")
    fun MaterialTextView.setOptionalText(text: String) {
        setText(text)
        isVisible = text.isNotEmpty()
    }

    @JvmStatic
    @BindingAdapter("negativeAmountText")
    fun MaterialTextView.setNegativeAmountText(amount: BigDecimal?) {
        val formattedAmountText = getCurrencyText(amount ?: BigDecimal.ZERO)
        val negativeAmountText = "-$formattedAmountText"
        text = negativeAmountText
    }
}
