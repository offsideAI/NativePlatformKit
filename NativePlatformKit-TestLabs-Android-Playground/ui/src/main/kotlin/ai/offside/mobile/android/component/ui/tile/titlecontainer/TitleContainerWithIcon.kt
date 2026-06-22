package ai.offside.mobile.android.component.ui.tile.titlecontainer

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.TitleContainerWithIconLayoutBinding

/**
 * TitleContainerWithIcon class - sets custom Title and Icon for component
 * @param context
 * @param attributeSet
 */
class TitleContainerWithIcon(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {
    private val binding: TitleContainerWithIconLayoutBinding =
        TitleContainerWithIconLayoutBinding.inflate(
            LayoutInflater.from(context), this, true
        )

    init {
        val attributeArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TitleContainerWithIcon, 0, 0)

        setContainerTitle(
            attributeArray.getString(R.styleable.TitleContainerWithIcon_containerTitle).orEmpty()
        )

        attributeArray.getDrawable(R.styleable.TitleContainerWithIcon_containerIcon)?.let {
            setContainerIcon(it)
        }

        attributeArray.recycle()
    }

    /** Method that sets the Title
     * @param containerTitle
     * */
    fun setContainerTitle(containerTitle: String) {
        binding.titleContainerTitle.text = containerTitle
    }

    /** Method that sets the Icon
     * @param containerIcon
     * */
    fun setContainerIcon(containerIcon: Drawable) {
        binding.titleContainerIcon.setImage(containerIcon)
    }

}