package ai.offside.mobile.android.component.ui.inputfield

import android.text.InputFilter
import android.text.Spanned
import java.math.BigDecimal
import java.util.Currency
import java.util.Locale

/**
 * Amount input field filter to check range of user entered amount.
 * Allow user to enter amount input in a range: minValue (0) - maxValue (9999999.99)
 */
class AmountInputFieldFilter : InputFilter {

    private val minAmountValue = BigDecimal(AmountInputFieldConstant.minRange)
    private val maxAmountValue = BigDecimal(AmountInputFieldConstant.maxRange)
    private val currencySymbol: String = Currency.getInstance("USD").getSymbol(Locale.US)
    private val regexExpression = "[$currencySymbol,]" // w/o dot (.)

    override fun filter(
        source: CharSequence?,
        start: Int,
        end: Int,
        dest: Spanned?,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        try {
            val input = BigDecimal(
                (dest.toString() + source.toString())
                    .replace(regexExpression.toRegex(), "")
            )

            if (isInRange(minAmountValue, maxAmountValue, input)) {
                return null // With null allow user to enter text - amount
            }
        } catch (nfe: NumberFormatException) {
            return ""
        }
        return ""
    }

    /**
     * Method to check if input amount is in range or not.
     *
     * @param min min-range for user input
     * @param max max-range for user input
     * @param input user input
     * @return boolean to indicate if user entered input is in min-max range or not, true if its in range
     */
    private fun isInRange(
        min: BigDecimal,
        max: BigDecimal,
        input: BigDecimal
    ): Boolean = input in min..max

}