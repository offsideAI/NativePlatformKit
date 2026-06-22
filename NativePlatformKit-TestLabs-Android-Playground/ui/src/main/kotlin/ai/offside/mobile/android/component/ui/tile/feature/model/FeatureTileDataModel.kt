package ai.offside.mobile.android.component.ui.tile.feature.model

import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel

class FeatureTileDataModel(
    val tileData: FeatureTileData,
    val sliderData: SliderComponentDataModel,
    val sliderEnabled: Boolean = true,
)