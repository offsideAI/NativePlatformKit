package ai.offside.mobile.android.component.ui.tile.feature.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import ai.offside.mobile.android.component.ui.tile.feature.model.FeatureTileDataModel


abstract class BaseFeatureTileViewHolder(tileView: View) : RecyclerView.ViewHolder(tileView) {

    abstract fun bindView(featureTileData: FeatureTileDataModel)

}