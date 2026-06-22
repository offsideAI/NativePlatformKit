package ai.offside.mobile.android.component.ui.tile.deposit

import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import ai.offside.mobile.android.component.ui.R

object DepositActivityTileBindingAdapter {
    /**
     * set top and bottom padding for tile depending upon it's children visibility
     * i.e secondary row text is gone keep top/bottom padding of xxlarge, if visible set secondary row text top/bottom padding xsmall
     * @param layout -> padding to be applied on
     * @param isSecondaryTextRowGone -> condition to determine top/bottom padding.
     */
    @JvmStatic
    @BindingAdapter("app:setSingleLinePadding")
    fun ConstraintLayout.setSingleLinePadding(isSecondaryTextRowGone: Boolean) {
        this.apply {
            val padding = if (isSecondaryTextRowGone) {
                resources.getDimensionPixelSize(R.dimen.padding_xxlarge)
            } else {
                resources.getDimensionPixelSize(R.dimen.padding_xsmall)
            }
            setPadding(paddingLeft, padding, paddingRight, padding)
        }
    }
}