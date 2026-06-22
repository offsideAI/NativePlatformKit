package ai.offside.mobile.android.component.ui.a11y

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat

/**
 * Function to set content description and role description to the Constraint Layout
 *
 * @param contentDescription contentDescription to the layout
 * @param roleDescription role description to the layout.
 */
fun ConstraintLayout.modifyRoleDescription(contentDescription: String, roleDescription: String, descendantFocusability: Int = ViewGroup.FOCUS_BLOCK_DESCENDANTS) {
    this.descendantFocusability = descendantFocusability
    ViewCompat.setAccessibilityDelegate(this, object : AccessibilityDelegateCompat() {
        override fun onInitializeAccessibilityNodeInfo(
            host: View,
            info: AccessibilityNodeInfoCompat
        ) {
            super.onInitializeAccessibilityNodeInfo(host, info)
            info.contentDescription = contentDescription
            info.roleDescription = roleDescription
        }
    })
}