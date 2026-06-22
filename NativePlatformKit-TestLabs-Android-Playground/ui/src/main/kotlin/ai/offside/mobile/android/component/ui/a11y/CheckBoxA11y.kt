package ai.offside.mobile.android.component.ui.a11y

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.TouchDelegate
import android.view.View
import android.view.ViewParent
import com.google.android.material.checkbox.MaterialCheckBox

/**
 * Custom CheckBox with a TouchDelegate to increase the touch area for better clickable experience
 */
class CheckBoxA11y @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : MaterialCheckBox(context, attributeSet) {

    /**
     * Method to set the increase the touch area of checkbox
     * @param parentView parentView
     *
     * Note: need to find a better way to set this within custom class and make it a private method.
     */
    fun setTouchDelegateForCheckbox(parentView: ViewParent) {
        val parent = parentView.parent as? View
        parent?.post {
            val hitRect = Rect()
            parent.getHitRect(hitRect)
            hitRect.right = hitRect.right - hitRect.left
            hitRect.bottom = hitRect.bottom - hitRect.top
            hitRect.top = 0
            hitRect.left = 0
            parent.touchDelegate = TouchDelegate(hitRect, this)
        }
    }
}