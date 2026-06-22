package ai.offside.mobile.android.helper.testlabs

import android.util.Log
import android.view.View
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ai.offside.mobile.android.component.ui.slider.data.SliderButtonData
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataModel
import ai.offside.mobile.android.component.ui.slider.data.SliderComponentDataProvider

class SliderViewModel : ViewModel() {
    private val _slider1Data = MutableLiveData<SliderComponentDataModel>().apply {
        value = SliderComponentDataModel(
            SliderComponentDataProvider.sliderComponentDataWithOnlyLeftButton(
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                )
            )
        )
    }

    private val _slider2Data = MutableLiveData<SliderComponentDataModel>().apply {
        value = SliderComponentDataModel(
            SliderComponentDataProvider.getSliderComponentDataWithLeftButton(
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                )
            )
        )
    }

    private val _slider3Data = MutableLiveData<SliderComponentDataModel>().apply {
        value = SliderComponentDataModel(
            SliderComponentDataProvider.getSliderComponentDataWithLeftButton(
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Middle Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                )
            )
        )
    }

    private val _slider4Data = MutableLiveData<SliderComponentDataModel>().apply {
        value = SliderComponentDataModel(
            SliderComponentDataProvider.getSliderComponentDataWithLeftButton(
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Right Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Middle Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                ),
                getSliderButtonData(
                    "Left Icon Button",
                    ai.offside.mobile.android.component.ui.R.drawable.ic_decorative_placeholder_regular
                )
            )
        )
    }

    val slider1Data: LiveData<SliderComponentDataModel> = _slider1Data
    val slider2Data: LiveData<SliderComponentDataModel> = _slider2Data
    val slider3Data: LiveData<SliderComponentDataModel> = _slider3Data
    val slider4Data: LiveData<SliderComponentDataModel> = _slider4Data

    fun getSliderButtonData(label: String, @DrawableRes iconRes: Int): SliderButtonData {
        return object : SliderButtonData {
            override val label = label
            override val icon = iconRes
            override fun onButtonClick(button: SliderButtonData) {
                Log.d("TEST", "Slider Button Clicked $label")
            }
        }
    }
}