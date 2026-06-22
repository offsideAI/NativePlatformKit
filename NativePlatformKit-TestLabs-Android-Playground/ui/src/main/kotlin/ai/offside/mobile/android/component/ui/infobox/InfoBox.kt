package ai.offside.mobile.android.component.ui.infobox

import android.content.Context
import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.annotation.StyleRes
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.core.widget.ImageViewCompat
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.InfoBoxLayoutBinding


/**
 * Icon State for info box. This state helps define what icon is visible in the InfoBox
 */
enum class InfoBoxState {
    INFORMATION_BLUE,
    INFORMATION_GREY,
    WARNING_YELLOW,
    WARNING_RED,
    SUCCESS_GREEN,
    FULL_WIDTH,
    NO_CONTAINER,
    INLINE,
}

/**
 * Info Box Custom Component Class. Helps maintain the styling and content description according to state of the component
 *
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class InfoBox @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewFilledStyle
) : MaterialCardView(context, attrs, defaultStyleAttr) {
    private val binding = InfoBoxLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * Updates the visibility of the icon
     *
     * @param showIcon
     */
    fun setShowIcon(showIcon: Boolean = false) {
        binding.iconView.isVisible = showIcon
    }

    /**
     * Updates the state of the Info Box. Updates styling based on the state
     *
     * @param state
     */
    fun setState(state: InfoBoxState) {
        when (state) {
            InfoBoxState.INFORMATION_BLUE -> applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Blue)
            InfoBoxState.INFORMATION_GREY -> applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Grey)
            InfoBoxState.WARNING_YELLOW -> applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Yellow)
            InfoBoxState.WARNING_RED -> applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Red)
            InfoBoxState.SUCCESS_GREEN -> applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Green)
            InfoBoxState.FULL_WIDTH -> {
                val horizontalPadding = resources.getDimension(R.dimen.gutter_small).toInt()
                val verticalPadding = resources.getDimension(R.dimen.spacing_unset).toInt()
                binding.infoBox.setPadding(
                    horizontalPadding,
                    verticalPadding,
                    horizontalPadding,
                    verticalPadding
                )
                applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Grey_Transparent)
                //Icons are always visible for this state
                binding.iconView.isVisible = true
            }

            InfoBoxState.NO_CONTAINER -> {
                val horizontalPadding = resources.getDimension(R.dimen.padding_xxlarge).toInt()
                val verticalPadding = resources.getDimension(R.dimen.spacing_unset).toInt()
                binding.infoBox.setPadding(
                    horizontalPadding,
                    verticalPadding,
                    horizontalPadding,
                    verticalPadding
                )
                applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Grey_Transparent)
                //Icons are always visible for this state
                binding.iconView.isVisible = true
            }

            InfoBoxState.INLINE -> {
                val inlinePadding = resources.getDimension(R.dimen.spacing_unset).toInt()
                val iconEndPadding = resources.getDimension(R.dimen.padding_xsmall).toInt()
                binding.infoBox.setPadding(
                    inlinePadding,
                    inlinePadding,
                    inlinePadding,
                    inlinePadding
                )
                binding.iconView.setPadding(
                    inlinePadding,
                    inlinePadding,
                    iconEndPadding,
                    inlinePadding
                )
                binding.description.setPadding(
                    inlinePadding,
                    inlinePadding,
                    inlinePadding,
                    inlinePadding
                )
                applyFromStyle(R.style.Offside_Redesign_InfoBox_StateStyle_Grey_Transparent)
                //Icons are always visible for this state
                binding.iconView.isVisible = true
                binding.topSpace.isVisible = false
                binding.bottomSpace.isVisible = false
            }
        }
    }


    /**
     * Applies the attribute style from the provided style res
     */
    private fun applyFromStyle(@StyleRes styleRes: Int) {
        val ta = context.obtainStyledAttributes(styleRes, R.styleable.InfoBox)

        setCardBackgroundColor(ta.getColor(R.styleable.InfoBox_backgroundColor, 0))
        binding.description.setTextColor(ta.getColor(R.styleable.InfoBox_textViewColor, 0))
        binding.iconView.setImageDrawable(ta.getDrawable(R.styleable.InfoBox_drawable))
        binding.iconView.setTint(ta.getColor(R.styleable.InfoBox_drawableTint, 0))
        binding.iconView.contentDescription =
            ta.getString(R.styleable.InfoBox_drawableContentDescription)
        ta.recycle()
    }

    /**
     * Set the info box title
     *
     * @param text
     */
    fun setInfoTitle(text: String) {
        binding.title.text = text
        binding.title.isVisible = text.isNotEmpty()
    }

    /**
     * Set the info box description
     *
     * @param text
     */
    fun setInfoText(text: String?) {
        binding.description.text = text
    }

    /**
     * Updates the text view
     *
     * @param spannableString
     */
    fun setInfoTextWithLink(spannableString: SpannableString?) {
        binding.description.text = spannableString
        binding.description.movementMethod = LinkMovementMethod.getInstance()
        ViewCompat.enableAccessibleClickableSpanSupport(binding.description)
    }

    /**
     * Helper method to update the tint of the ShapeableImageView
     *
     * @param colorInt
     */
    private fun ShapeableImageView.setTint(@ColorInt colorInt: Int) {
        ImageViewCompat.setImageTintList(
            this,
            ColorStateList.valueOf(colorInt)
        )
    }
}