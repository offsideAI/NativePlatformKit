package ai.offside.mobile.android.component.ui.inputfield

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputEditText
import ai.offside.mobile.android.component.ui.inputfield.AmountInputFieldTextWatcher.AmountInputTextChangeListener
import java.lang.ref.WeakReference
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

/**
 * Amount input field text watcher. Use this TextWatcher class to format user input - amount.
 *
 * @property inputEditText view reference of [TextInputEditText]
 * @property inputTextChangeListener listener [AmountInputTextChangeListener] to pass callback to parent view
 */
class AmountInputFieldTextWatcher(
    private val inputEditText: TextInputEditText,
    private val inputTextChangeListener: AmountInputTextChangeListener
) : TextWatcher {

    private val inputEditTextWeakReference = WeakReference(inputEditText)
    private val maxAmountValue = BigDecimal(AmountInputFieldConstant.maxRange)
    private var currencySymbol: String = Currency.getInstance("USD").getSymbol(Locale.US)
    private val regexExpression = "[$currencySymbol,.]" // with dot (.)

    interface AmountInputTextChangeListener {
        /**
         * Method callback to notify parent view that somewhere within s the text has been changed.
         *
         * @param s changed text, raw input
         * @param formattedInput formatted amount, limit to 2 decimal digits and w/o "$" sign
         */
        fun afterTextChange(s: Editable?, formattedInput: BigDecimal?)
    }

    private fun isGreaterOrEqual(comparing: BigDecimal, compared: BigDecimal): Boolean {
        return comparing >= compared
    }

    private fun isLesser(comparing: BigDecimal, compared: BigDecimal): Boolean {
        return !isGreaterOrEqual(comparing, compared)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

        if (TextUtils.isEmpty(s))
            return

        val formattedInput = s?.let {
            var enteredAmount = s.toString()
            enteredAmount = enteredAmount.replace(regexExpression.toRegex(), "")
            var parsed: BigDecimal = BigDecimal(enteredAmount).setScale(2, RoundingMode.FLOOR)
                .divide(BigDecimal(100), RoundingMode.FLOOR)
            // When user try to input which is greater than max limit.
            while (isLesser(maxAmountValue, parsed)) {
                parsed = parsed.divide(BigDecimal(10), RoundingMode.FLOOR)
                    .setScale(2, RoundingMode.FLOOR)
            }
            parsed
        }

        formattedInput?.let {
            val editText: TextInputEditText? = inputEditTextWeakReference.get()
            editText?.removeTextChangedListener(this)
            val formattedInputString: String =
                NumberFormat.getCurrencyInstance(Locale.US).format(it)
            editText?.setText(formattedInputString)
            editText?.setSelection(formattedInputString.length)
            editText?.addTextChangedListener(this)
        }

        inputTextChangeListener.afterTextChange(s, formattedInput)
    }
}