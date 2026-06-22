package ai.offside.mobile.android.component.ui.tile.account.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.divider.CustomMaterialDivider
import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout
import ai.offside.mobile.android.component.ui.slider.data.OnSwipeListener
import ai.offside.mobile.android.component.ui.tile.account.data.AccountTileDataModel

/**
 * Recycler Adapter class for Account Tile with Slider Component
 *
 * @param layoutManager
 */
class AccountTileWithSliderRecyclerAdapter(
    private val layoutManager: AccountTileRecyclerViewLayoutManager
) : RecyclerView.Adapter<AccountTileViewHolder>() {

    private var accounts: MutableList<AccountTileDataModel> = mutableListOf()
    private val sliders = mutableListOf<SliderComponentLayout>()

    fun setAccountTileItems(items: List<AccountTileDataModel>) {
        accounts = items.toMutableList()
    }

    fun updateAccountTileItems(updatedAccountTileItems: List<AccountTileDataModel>) {
        val diffCallback = AccountTileDiffUtilCallback(accounts, updatedAccountTileItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        accounts.clear()
        accounts.addAll(updatedAccountTileItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AccountTileViewHolder {
        return initializeSlider(parent)
    }

    /**
     * Adds divider decoration to tile items
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val mContext = recyclerView.context

        val inset: Int = mContext.resources.getDimensionPixelSize(R.dimen.padding_xlarge)
        val dividerItemDecoration = CustomMaterialDivider.getCustomDivider(
            recyclerView.context,
            layoutManager.orientation,
            inset
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun getItemCount(): Int = accounts.size

    override fun onBindViewHolder(holder: AccountTileViewHolder, position: Int) {
        holder.bindView(accounts[position])
    }


    /**
     * Inflates Slider Component and sets swipe listener
     */
    private fun initializeSlider(parent: ViewGroup): AccountTileViewHolder {
        val slider = SliderComponentLayout(parent.context)
        slider.setOnSwipeListener(object : OnSwipeListener {
            override fun onSwipeStart(sliderComponentLayout: SliderComponentLayout) {
                resetOtherSliderComponents(sliderComponentLayout)
            }

            override fun onTouchUp() {
                enableVerticalScroll()
            }
        })
        sliders.add(slider)
        return AccountTileViewHolder(slider)
    }

    /**
     * Resets the state of slider view that user is no longer interacting with
     */
    fun resetOtherSliderComponents(sliderComponentLayout: SliderComponentLayout) {
        disableVerticalScroll()
        sliders.forEach {
            if (it != sliderComponentLayout) {
                it.resetState()
            }
        }
    }

    /**
     * Disables vertical scroll of layout manager of RV
     */
    private fun disableVerticalScroll() {
        layoutManager.setVerticalScroll(false)
    }

    /**
     * Enables vertical scroll of layout manager of RV
     */
    private fun enableVerticalScroll() {
        layoutManager.setVerticalScroll(true)
    }

    /**
     * DiffUtil used to refresh the list
     * Calculates the difference between 2 list and updates the respective list-item instead of whole list getting change
     */
    class AccountTileDiffUtilCallback(private val oldList: List<AccountTileDataModel>, private val newList: List<AccountTileDataModel>) :
        DiffUtil.Callback() {

        /** Old List item size */
        override fun getOldListSize(): Int = oldList.size

        /** New List item size */
        override fun getNewListSize(): Int = newList.size

        /**
         * @param oldItemPosition
         * @param newItemPosition
         * @return true if the label matches, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem.tileData.displayName == newItem.tileData.displayName
        }

        /**
         * @param oldItemPosition
         * @param newItemPosition
         * @return true if both class values are equal, if the items not same then the view will be redrawn (refreshed)
         */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }
    }
}