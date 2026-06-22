package ai.offside.mobile.android.component.ui.inputfield

import android.app.Service
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

class InputFieldUtils {

    companion object {

        @JvmStatic
        fun initiateFocus(context: Context?, view: View): Boolean {
            val didTakeFocus: Boolean = context?.let {
                val didTakeFocus = view.requestFocus()
                val imm = context.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(view, 0)
                didTakeFocus
            } == true
            return didTakeFocus
        }

        @JvmStatic
        fun hideKeyboard(context: Context?, viewHoldingKeyboard: View?) {
            context?.let {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                viewHoldingKeyboard?.let {
                    imm.hideSoftInputFromWindow(viewHoldingKeyboard.windowToken, 0)
                }
            }
        }
    }
}