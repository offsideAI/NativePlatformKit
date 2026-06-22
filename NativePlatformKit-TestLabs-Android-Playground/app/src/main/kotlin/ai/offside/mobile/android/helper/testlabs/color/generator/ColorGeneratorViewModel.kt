package ai.offside.mobile.android.helper.testlabs.color.generator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.map
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import ai.offside.mobile.android.component.ui.color.UIComponentColor
import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.view.View
import ai.offside.mobile.android.helper.testlabs.color.generator.transform.ColorGeneratorTransform

class ColorGeneratorViewModel constructor(
    private val applicationContext: Context
): ViewModel() {
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                ColorGeneratorViewModel(
                    applicationContext = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                )
            }
        }
    }

    val redInt: MutableLiveData<String> = MutableLiveData("")
    val blueInt: MutableLiveData<String> = MutableLiveData("")
    val greenInt: MutableLiveData<String> = MutableLiveData("")

    val generateDarkColors: MutableLiveData<Boolean> = MutableLiveData(false)

    private val _generatorState: MutableLiveData<ColorGeneratorState> = MutableLiveData(ColorGeneratorState.Initial)
    val colorGeneratorState: LiveData<ColorGeneratorState> = _generatorState.switchMap { list ->
        generateDarkColors.map { reversed ->
            when (list) {
                is ColorGeneratorState.FormType.Manual -> list.copy(reversed = reversed)
                is ColorGeneratorState.FormType.Selectors.FamilyColor -> list.copy(reversed = reversed)
                else -> list
            }
        }
    }

    private val _mainModel: MutableLiveData<ColorGeneratorModel> = MutableLiveData()
    val mainModel: LiveData<ColorGeneratorModel> = _mainModel

    val generatedModels: LiveData<List<ColorGeneratorModel>> = _mainModel.switchMap { main ->
        generateDarkColors.map { reversed ->
            when (val state = _generatorState.value) {
                is ColorGeneratorState.FormType.Selectors.FamilyColor ->
                    state.color.transformations
                else ->
                    ColorGeneratorTransform.transforms()
            }
                .let {
                    if (reversed) {
                        it.reversed()
                    } else {
                        it
                    }
                }
                .map { main.copy(transform = it) }
        }
    }

    fun onEvent(
        event: ColorGeneratorEvent
    ) {
        when (event) {
            ColorGeneratorEvent.Navigation.ToResults -> {
                _generatorState.postValue(
                    ColorGeneratorState.Initial
                )
            }
            else -> {}
        }
    }

    fun onFamilyChosen(
        family: UIComponentColor.Family
    ) {
        _generatorState.postValue(
            ColorGeneratorState.FormType.Selectors.Family(
                family = family,
                colors = family.colors
            )
        )
    }

    @SuppressLint("ResourceType")
    fun onFamilyColorChosen(
        color: UIComponentColor
    ) {
        val selectedColor = ColorGeneratorModel(
            hexString = applicationContext.getString(color.colorRes)
        )
        val colors = when (color.transformationStops) {
            0 -> ColorGeneratorTransform.transforms().map { selectedColor.copy(transform = it) }
            else -> color.transformations.map { selectedColor.copy(transform = it) }
        }

        _mainModel.postValue(selectedColor)

        _generatorState.postValue(
            ColorGeneratorState.FormType.Selectors.FamilyColor(
                color = color,
                model = selectedColor,
                generated = colors
            )
        )
    }

    fun calculateColors(
        button: View,
    ) {
        val red = when {
            redInt.isInitialized -> redInt.value!!.ifBlank { "0" }
            else -> "0"
        }.toInt()
        val green = when {
            greenInt.isInitialized -> greenInt.value!!.ifBlank { "0" }
            else -> "0"
        }.toInt()
        val blue = when {
            blueInt.isInitialized -> blueInt.value!!.ifBlank { "0" }
            else -> "0"
        }.toInt()

        val base = ColorGeneratorModel(red, green, blue)
        val colors = ColorGeneratorTransform.transforms().map { base.copy(transform = it) }

        _mainModel.postValue(base)

        _generatorState.postValue(
            ColorGeneratorState.FormType.Manual(
                chosen = base,
                generated = colors,
            )
        )
    }
}