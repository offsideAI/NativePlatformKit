package ai.offside.mobile.android.component.ui.tile.feature.recyclerview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout
import ai.offside.mobile.android.component.ui.slider.data.OnSwipeListener
import ai.offside.mobile.android.component.ui.tile.account.recyclerview.AccountTileRecyclerViewLayoutManager
import ai.offside.mobile.android.component.ui.tile.feature.FeatureTile
import ai.offside.mobile.android.component.ui.tile.feature.model.FeatureTileDataModel

class FeatureTileWithSliderRecyclerAdapter(
    private val layoutManager: AccountTileRecyclerViewLayoutManager
) : RecyclerView.Adapter<BaseFeatureTileViewHolder>() {

    companion object {
        const val SLIDER_VIEW = 1
        const val FEATURE_VIEW = 2
    }

    private var featureTiles: MutableList<FeatureTileDataModel> = mutableListOf()
    private val sliders = mutableListOf<SliderComponentLayout>()

    fun setFeatureTileItems(items: List<FeatureTileDataModel>) {
        featureTiles = items.toMutableList()
    }

    fun updateFeatureTileItems(updatedFeatureTiles: List<FeatureTileDataModel>) {
        val diffCallback = FeatureTileDiffUtilCallback(featureTiles, updatedFeatureTiles)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        featureTiles.clear()
        featureTiles.addAll(updatedFeatureTiles)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseFeatureTileViewHolder {
        return when (viewType) {
            SLIDER_VIEW -> initializeSlider(parent)
            FEATURE_VIEW -> FeatureTileViewHolder(FeatureTile(parent.context))
            else -> {throw IllegalArgumentException("Invalid Item Type")}
        }
    }

    override fun onBindViewHolder(holder: BaseFeatureTileViewHolder, position: Int) {
        return when (featureTiles[position].sliderEnabled) {
            true -> (holder as FeatureTileSliderViewHolder).bindView(featureTiles[position])
            false -> (holder as FeatureTileViewHolder).bindView(featureTiles[position])
        }
    }

    override fun getItemCount(): Int = featureTiles.size

    override fun getItemViewType(position: Int): Int =
        when (featureTiles[position].sliderEnabled) {
            true -> SLIDER_VIEW
            false -> FEATURE_VIEW
        }

    /**
     * Inflates Slider Component and sets swipe listener
     */
    private fun initializeSlider(parent: ViewGroup): FeatureTileSliderViewHolder {
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
        return FeatureTileSliderViewHolder(slider)
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
    class FeatureTileDiffUtilCallback(private val oldList: List<FeatureTileDataModel>, private val newList: List<FeatureTileDataModel>) :
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
            return oldItem.tileData.title == newItem.tileData.title
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