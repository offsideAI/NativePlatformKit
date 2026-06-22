package ai.offside.mobile.android.component.ui.a11y

import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.view.TouchDelegate
import android.view.View
import android.view.ViewParent
import android.view.accessibility.AccessibilityNodeInfo
import com.google.android.material.radiobutton.MaterialRadioButton

/**
 * Custom RadioButton with a TouchDelegate to increase the touch area for better clickable experience
 */
class RadioButtonA11y @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null
) : MaterialRadioButton(context, attributeSet) {

    /**
     * Method to set the role description for radiobutton
     * @param info AccessibilityNodeInfo to access view accessibility information
     */
    override fun onInitializeAccessibilityNodeInfo(info: AccessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(info)

        if(isChecked){
            info.removeAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
            info.isClickable = false
        } else {
            info.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK)
            info.isClickable = true
        }
    }

    /**
     * Method to set the increase the touch area of radiobutton
     * @param parentView parentView
     *
     * Note: need to find a better way to set this within custom class and make it a private method.
     */
    fun setTouchDelegateForRadioButton(parentView: ViewParent) {
        val parent = parentView as? View
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