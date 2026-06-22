package ai.offside.mobile.android.helper.testlabs.nav.inputfield

import android.content.Context
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo.*
import android.view.inputmethod.InputMethodManager
import com.google.android.material.textfield.TextInputEditText

/**
 * Function for editotActionListener for the input fields.
 * clear focus and hide softkeyboard on key event
 *
 * @receiver [TestUIRedesignAmountInputFieldFragment] [TestUIRedesignInputFieldFragment]
 */
fun TextInputEditText.setOnEditFieldActionListener() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    this.setOnEditorActionListener { v, actionId, event ->
        if (actionId == IME_ACTION_DONE || actionId == IME_ACTION_NEXT ||
            (event != null && event.keyCode == KeyEvent.ACTION_DOWN)) {
            this.clearFocus()
            imm.hideSoftInputFromWindow(v.windowToken, 0)
        }
        return@setOnEditorActionListener false
    }
}