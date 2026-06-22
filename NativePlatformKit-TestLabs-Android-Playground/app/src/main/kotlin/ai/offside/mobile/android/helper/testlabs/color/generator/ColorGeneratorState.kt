package ai.offside.mobile.android.helper.testlabs.color.generator

import com.google.android.material.button.MaterialButton
import ai.offside.mobile.android.component.ui.color.UIComponentColor
import ai.offside.mobile.android.helper.testlabs.R

sealed class ColorGeneratorState {
    data object Initial : ColorGeneratorState()

    sealed class FormType : ColorGeneratorState() {
        abstract fun colors(): List<ColorGeneratorModel>

        data class Manual(
            private val chosen: ColorGeneratorModel,
            private val generated: List<ColorGeneratorModel>,
            private val reversed: Boolean = false,
        ) : FormType() {
            override fun colors(): List<ColorGeneratorModel> =
                when {
                    reversed -> listOf(chosen).plus(generated.reversed())
                    else -> listOf(chosen).plus(generated)
                }
        }

        sealed class Selectors : FormType() {
            abstract fun configureColorChooser(button: MaterialButton)

            data class Family(
                val family: UIComponentColor.Family,
                val colors: List<UIComponentColor>
            ) : Selectors() {
                override fun colors(): List<ColorGeneratorModel> = emptyList()
                override fun configureColorChooser(button: MaterialButton) {
                }
            }

            data class FamilyColor(
                val color: UIComponentColor,
                private val model: ColorGeneratorModel,
                private val generated: List<ColorGeneratorModel>,
                private val reversed: Boolean = false
            ) : Selectors() {
                override fun colors(): List<ColorGeneratorModel> =
                    when {
                        reversed -> listOf(model).plus(generated.reversed())
                        else -> listOf(model).plus(generated)
                    }

                override fun configureColorChooser(button: MaterialButton) {
                    button.text = button.context.getString(
                        R.string.color_generator_selector_color_selected,
                        (color as Enum<*>).name,
                        color.colorString(context = button.context)
                    )
                }
            }
        }
    }

}