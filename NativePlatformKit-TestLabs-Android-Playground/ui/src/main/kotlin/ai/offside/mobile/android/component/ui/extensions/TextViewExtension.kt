package ai.offside.mobile.android.component.ui.extensions

import android.text.TextUtils
import android.text.TextUtils.TruncateAt
import androidx.core.view.doOnPreDraw
import com.google.android.material.textview.MaterialTextView
import java.lang.StringBuilder

/**
 * Extension function to set text with middle ellipses and suffix text.
 * E.g.
 * Given inputText = "Acme Business Options® Visa Signature® Credit Card" AND suffixText = "x1226"
 * Then set text = "Acme Business Options® Visa...x1226".
 * Note: inputText get ellipsized depending on available width on text-view.
 *
 * @param inputText input text to set to textView [MaterialTextView] with ellipses (...)
 * @param suffixText suffix text to apply at end of ellipses (...)
 */
fun MaterialTextView.setTextWithMiddleEllipses(inputText: String, suffixText: String) =
    doOnPreDraw {
        val drawableLeftIndex = 0
        val drawableArray = compoundDrawables
        var viewWidth = (measuredWidth - paddingStart - paddingEnd).toFloat()
        drawableArray[drawableLeftIndex]?.let {
            if (drawableArray[drawableLeftIndex]!!.bounds.width() > 0) {
                viewWidth -= drawableArray[drawableLeftIndex]!!.bounds.width().toFloat()
            }
        }
        val paint = layout.paint
        val originalText = TextUtils.concat(inputText, suffixText)
        var ellipsizedText = TextUtils.ellipsize(originalText, paint, viewWidth, TruncateAt.END)
        if (ellipsizedText.length < originalText.length) {
            val suffixTextWidth: Float = paint.measureText(suffixText, 0, suffixText.length)
            ellipsizedText = TextUtils.ellipsize(inputText, paint, viewWidth - suffixTextWidth, TruncateAt.END)
            val ellipsizedDisplayName = TextUtils.concat(ellipsizedText, suffixText)
            text = ellipsizedDisplayName
        } else {
            text = TextUtils.concat(inputText, " ", suffixText)
        }
    }

/**
 * This extension will add the space between characters.
 * When we accessibility reads numbers then we need to add space to read as individual number instead of value
 * 1 2 3 4 instead 1234
 */
fun String.addSpaceBetweenCharacter(): String {
    return this.toCharArray().joinTo(StringBuilder(), " ").toString()
}