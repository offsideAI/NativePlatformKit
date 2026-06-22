package ai.offside.mobile.android.component.ui.tile.account.recyclerview

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class AccountTileRecyclerViewLayoutManager(context: Context) : LinearLayoutManager(context) {
    private var verticalScroll = true;
    override fun canScrollVertically(): Boolean {
        return verticalScroll
    }

    fun setVerticalScroll(canScroll: Boolean) {
        verticalScroll = canScroll
    }
}