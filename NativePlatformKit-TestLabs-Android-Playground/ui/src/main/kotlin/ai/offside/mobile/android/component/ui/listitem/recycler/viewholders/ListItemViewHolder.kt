package ai.offside.mobile.android.component.ui.listitem.recycler.viewholders

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.core.view.AccessibilityDelegateCompat
import androidx.core.view.ViewCompat
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat
import androidx.core.view.children
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDivider
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.listitem.data.ListItemData

/**
 * Abstract View Holder class for List Item View
 *
 * @param viewBinding
 */
abstract class ListItemViewHolder(private val viewBinding: ViewBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    abstract fun bind(data: ListItemData, dividerVisibility: Int)

    /**
     * Returns [MaterialDivider]
     *
     * @param context
     */
    fun getDivider(context: Context): MaterialDivider {
        val divider = MaterialDivider(context)
        divider.dividerColor = MaterialColors.getColor(
            context,
            R.attr.offside_outline,
            ContextCompat.getColor(context, R.color.sys_color_neutral95)
        )
        return divider
    }

    /**
     * Sets enabled/disabled state for all views in list item
     *
     * @param enabled
     */
    fun setEnabled(enabled: Boolean) {
        val root = viewBinding.root
        root.isEnabled = enabled
        if (root is ViewGroup) {
            root.children.forEach {
                it.isEnabled = enabled
            }
        }
    }

    fun setButtonRole(view: View, description: CharSequence) {
        ViewCompat.setAccessibilityDelegate(view, object : AccessibilityDelegateCompat() {
            override fun onInitializeAccessibilityNodeInfo(
                host: View,
                info: AccessibilityNodeInfoCompat
            ) {
                super.onInitializeAccessibilityNodeInfo(host, info)
                info.apply {
                    contentDescription = description
                    roleDescription = Button::class.java.simpleName
                }
            }
        })
    }
}