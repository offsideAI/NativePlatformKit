package ai.offside.mobile.android.component.ui.tile.feature.recyclerview

import androidx.constraintlayout.widget.ConstraintLayout.LayoutParams
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.tile.feature.FeatureTile
import ai.offside.mobile.android.component.ui.tile.feature.model.FeatureTileDataModel

class FeatureTileViewHolder(private val featureTile: FeatureTile) :
    BaseFeatureTileViewHolder(featureTile) {

    override fun bindView(featureTileData: FeatureTileDataModel) {
        resetView(featureTileData)
    }

    /**
     * Removes all view in main content and adds new [FeatureTile]
     *
     * @param featureTileData
     */
    private fun resetView(featureTileData: FeatureTileDataModel) {
        featureTile.apply {
            layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
            setTileTitle(featureTileData.tileData.title)
            setTileSupplementary(featureTileData.tileData.subTitle)
            setTileBadgeCount(featureTileData.tileData.badgeCount)
            ContextCompat.getDrawable(context, featureTileData.tileData.icon)
                ?.let { setPrimaryIcon(it) }
            setDividerVisibility(featureTileData.tileData.dividerVisible)
            setMoreMenuActionListener { featureTileData.tileData.moreClickAction.invoke() }
            setOnClickListener { featureTileData.tileData.tileClickAction.invoke() }
        }
    }
}