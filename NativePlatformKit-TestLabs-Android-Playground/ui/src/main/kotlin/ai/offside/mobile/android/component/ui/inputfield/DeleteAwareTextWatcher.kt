package ai.offside.mobile.android.component.ui.inputfield

import android.text.Editable
import android.text.TextWatcher

open class DeleteAwareTextWatcher : TextWatcher {

    /**
     * Tracked between calls to [beforeTextChanged] and [onTextChanged]
     *   to modify the behavior of [withText]
     */
    protected var isDeleting: Boolean = false
        private set

    /**
     * Indicates if this field has been under the affects of user input at least
     *   once and allows for untouched states to be suppressed and mapped to more
     *   user-friendly alternatives before defaulting to an invalid state
     */
    protected var isPristine: Boolean = false
        private set

    /**
     * Optional transformation of the [Editable] input of [afterTextChanged]
     * @receiver The [Editable] invoked in [afterTextChanged]
     */
    protected open fun Editable.formatted(): Editable = this

    /**
     * Called when the [input] is about to change at [startOfReplacement] for [numberOfCharsToReplace]
     *   by a total of [lengthOfReplacementText] [Char]s
     * If [lengthOfReplacementText] is 0, we are deleting
     * @param input Current [androidx.appcompat.widget.AppCompatEditText] content
     * @param startOfReplacement Index of [input] where change is happening
     * @param numberOfCharsToReplace Count of [Char]s in [input] being changed
     * @param lengthOfReplacementText Count of [Char]s replacing [numberOfCharsToReplace] in [input]
     * @see <https://developer.android.com/reference/android/text/TextWatcher#beforeTextChanged(java.lang.CharSequence,%20int,%20int,%20int)>
     */
    open fun onBeforeTextChanged(
        input: CharSequence,
        startOfReplacement: Int,
        numberOfCharsToReplace: Int,
        lengthOfReplacementText: Int
    ) {}

    final override fun beforeTextChanged(
        s: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) {
        s?.let {
            isPristine = false
            isDeleting = after == 0
            onBeforeTextChanged(
                input = it,
                startOfReplacement = start,
                numberOfCharsToReplace = count,
                lengthOfReplacementText = after
            )
        }
    }

    /**
     * Called when the [input] is being changed starting at [startOfReplacement] and replacing
     *   [lengthOfOldText] [Char]s with [numberOfReplacementCharacters]
     * If [numberOfReplacementCharacters] is 0, we are deleting
     * @param input Current [androidx.appcompat.widget.AppCompatEditText] content
     * @param startOfReplacement Index of [input] where change is happening
     * @param lengthOfOldText Count of [Char]s that are changing
     * @param numberOfReplacementCharacters Count of [Char]s replacing [lengthOfOldText] [Char]s in [input]
     * @see <https://developer.android.com/reference/android/text/TextWatcher#onTextChanged(java.lang.CharSequence,%20int,%20int,%20int)>
     */
    open fun afterOnTextChanged(
        input: CharSequence,
        startOfReplacement: Int,
        lengthOfOldText: Int,
        numberOfReplacementCharacters: Int
    ) {}

    final override fun onTextChanged(
        s: CharSequence?,
        start: Int,
        before: Int,
        count: Int
    ) {
        s?.let {
            if (!isDeleting && count == 0) {
                isDeleting = true
            }
            afterOnTextChanged(
                input = it,
                startOfReplacement = start,
                lengthOfOldText = before,
                numberOfReplacementCharacters = count
            )
        }
    }

    open fun withText(input: String) {}
    final override fun afterTextChanged(s: Editable?) {
        s?.let {
            if (it.isNotBlank()) {
                withText(it.formatted().toString())
            }
        }
    }
}