package ai.offside.mobile.android.component.ui.tile.accounttransactions.bindingadapter

import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import ai.offside.mobile.android.component.ui.R

/**
 * Binding adapter class for setting the margin of description textview.
 */
class AccountTransactionBindingAdapter {
    companion object {
        /**
         * Set layout margin of description textview depending  on the visibility of the deposit icon imageview.
         * @param view : description textview.
         * @param visibility: visibility of the deposit icon image view.
         */
        @BindingAdapter("layoutMarginDependingOnVisibility")
        @JvmStatic
        fun setLayoutMarginDependingOnVisibility(view: View, visibility: Int) {
            val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
            val marginTop = view.context.resources.getDimensionPixelSize(R.dimen.margin_small)
            val marginEnd = view.context.resources.getDimensionPixelSize(R.dimen.margin_medium)
            val marginStart = view.context.resources.getDimensionPixelSize(R.dimen.margin_xsmall)
            val paddingStart = view.context.resources.getDimensionPixelSize(R.dimen.padding_xsmall)
            val spacingUnset = view.context.resources.getDimensionPixelSize(R.dimen.spacing_unset)
            when

                (visibility) {
                View.VISIBLE -> {
                    layoutParams.setMargins(spacingUnset, marginTop, marginEnd, spacingUnset)
                    view.setPadding(paddingStart, spacingUnset, spacingUnset, spacingUnset)
                }

                else -> {
                    layoutParams.setMargins(marginStart, marginTop, marginEnd, spacingUnset)
                    view.setPadding(spacingUnset, spacingUnset, spacingUnset, spacingUnset)
                }
            }
            view.layoutParams = layoutParams
        }
    }
}