package ai.offside.mobile.android.component.ui.color

import androidx.annotation.ColorRes
import androidx.annotation.UiContext
import android.annotation.SuppressLint
import android.content.Context
import android.view.Menu
import ai.offside.mobile.android.component.ui.color.generator.transform.ColorGeneratorTransformer

sealed interface UIComponentColor {
    @get:ColorRes val colorRes: Int

    @SuppressLint("ResourceType")
    fun colorString(
        context: Context
    ): String =
        context.getString(colorRes)

    fun addToMenu(
        menu: Menu,
        @UiContext context: Context,
    ) {
        menu.add(
            Menu.NONE,
            colorRes,
            Menu.NONE,
            "${(this as Enum<*>).name} ${colorString(context = context)}",
        )
    }

    val transformationStops: Int get() = 0
    val transformations: List<ColorGeneratorTransformer> get() = listOf()

    interface Aqua : UIComponentColor
    interface Azurite : UIComponentColor
    interface Blue : UIComponentColor
    interface Red : UIComponentColor
    interface Graphite : UIComponentColor
    interface Green : UIComponentColor
    interface Lime : UIComponentColor
    interface Orange : UIComponentColor
    interface Purple : UIComponentColor
    interface Sand : UIComponentColor
    interface Yellow : UIComponentColor

    interface DataViz : UIComponentColor

    enum class Family {
        MISSING,
        AQUA,
        AZURITE,
        BLUE,
        RED,
        GRAPHITE,
        GREEN,
        LIME,
        ORANGE,
        PURPLE,
        SAND,
        YELLOW,
        DATA_VIZ,
        ;

        val colors: List<UIComponentColor>
            get() = when (this) {
                MISSING -> emptyList()
                AQUA -> UIComponentAqua.entries
                AZURITE -> UIComponentAzurite.entries
                BLUE -> UIComponentBlue.entries
                RED -> UIComponentRed.entries
                GRAPHITE -> UIComponentGraphite.entries
                GREEN -> UIComponentGreen.entries
                LIME -> UIComponentLime.entries
                ORANGE -> UIComponentOrange.entries
                PURPLE -> UIComponentPurple.entries
                SAND -> UIComponentSand.entries
                YELLOW -> UIComponentYellow.entries
                DATA_VIZ -> UIComponentDataViz.entries
            }
    }

    companion object {
        val families: List<Family>
            get() = Family.entries.filterNot { it == Family.MISSING }

        fun readFamily(familyName: String): Family =
            Family.valueOf(familyName)
    }
}