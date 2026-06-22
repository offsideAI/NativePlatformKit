package ai.offside.mobile.android.component.ui.tile.feature.recyclerview

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.slider.SliderComponentLayout
import ai.offside.mobile.android.component.ui.tile.feature.FeatureTile
import ai.offside.mobile.android.component.ui.tile.feature.model.FeatureTileDataModel

class FeatureTileSliderViewHolder(private val sliderComponentLayout: SliderComponentLayout) :
    BaseFeatureTileViewHolder(sliderComponentLayout) {

    override fun bindView(featureTileData: FeatureTileDataModel) {
        resetView(featureTileData)
    }

    /**
     * Removes all view in main content and adds new [FeatureTile]
     *
     * @param featureTileData
     */
    private fun resetView(featureTileData: FeatureTileDataModel) {
        val context = sliderComponentLayout.context
        val featureTile = FeatureTile(context)
        featureTile.apply {
            setTileTitle(featureTileData.tileData.title)
            setTileSupplementary(featureTileData.tileData.subTitle)
            setTileBadgeCount(featureTileData.tileData.badgeCount)
            ContextCompat.getDrawable(context, featureTileData.tileData.icon)?.let { setPrimaryIcon(it) }
            setDividerVisibility(featureTileData.tileData.dividerVisible)
            setMoreMenuActionListener { featureTileData.tileData.moreClickAction.invoke() }
        }
        sliderComponentLayout.setSliderComponentData(featureTileData.sliderData)
        sliderComponentLayout.setMainContent(featureTile)
        sliderComponentLayout.setMainContentClickAction { featureTileData.tileData.tileClickAction.invoke() }
    }

}