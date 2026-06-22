package ai.offside.mobile.android.component.ui.divider

import android.content.Context
import androidx.core.content.ContextCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.divider.MaterialDividerItemDecoration
import ai.offside.mobile.android.component.ui.R

/**
 * Custom Divider class for recycler view
 */
class CustomMaterialDivider private constructor(context: Context, orientation: Int) :
    MaterialDividerItemDecoration(context, orientation) {
    companion object {
        @JvmStatic
        fun getCustomDivider(
            context: Context,
            orientation: Int,
            intent: Int,
            isLastItemDecorated: Boolean = false
        ): CustomMaterialDivider {
            val customDivider = CustomMaterialDivider(context, orientation)
            customDivider.dividerInsetStart = intent
            customDivider.isLastItemDecorated = isLastItemDecorated
            customDivider.dividerColor = MaterialColors.getColor(
                context,
                R.attr.offside_outline,
                ContextCompat.getColor(context, R.color.sys_color_neutral95)
            )
            return customDivider
        }
    }
}