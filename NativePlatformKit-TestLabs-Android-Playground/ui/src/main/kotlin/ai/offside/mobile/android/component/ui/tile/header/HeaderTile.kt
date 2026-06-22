package ai.offside.mobile.android.component.ui.tile.header

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.badges.NotificationBadge
import ai.offside.mobile.android.component.ui.databinding.HeaderTileBinding
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.ViewCompat

/**
 * @param context
 * @param attributeSet [AttributeSet] also holds the custom component data defined as [R.styleable.FeatureTile]
 *  - tileTitle : Title of the Header
 *  - tileSupplementary : Supplementary or Sub Title, default will be GONE state
 *  - sopButton : Optional, default will be GONE state
 *  - tileBadgeCount : Tile badge count, provide tilBadgeContentDescription for accessibility for SoP Button
 *  - tileBadgeContentDescriptionLabel : Accessibility content for the badge
 *
 *  The badge can be added dynamically from program using [setTileBadgeCount]
 */
class HeaderTile(
    context: Context,
    attributeSet: AttributeSet? = null
): ConstraintLayout(context, attributeSet) {

    private val binding: HeaderTileBinding = HeaderTileBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attributeArray = context.obtainStyledAttributes(attributeSet, R.styleable.HeaderTile, 0, 0)

        setTileTitle(attributeArray.getString(R.styleable.HeaderTile_titleText).orEmpty())

        attributeArray.getString(R.styleable.HeaderTile_subtitleText)?.let {
            setTileSupplementary(it)
        }

        setTileTitleLogo(
            attributeArray.getResourceId(R.styleable.HeaderTile_titleTextLogo, 0),
            attributeArray.getFloat(R.styleable.HeaderTile_scaleFactor, 1.0f)
        )


        val sopButtonText = attributeArray.getString(R.styleable.HeaderTile_sopButtonText).orEmpty()

        val sopButtonIcon = attributeArray.getDrawable(R.styleable.HeaderTile_sopButtonIcon)

        val sopButtonVisible = attributeArray.getBoolean(R.styleable.HeaderTile_sopButtonVisibility, false)

        setSopButton(sopButtonText, sopButtonIcon, sopButtonVisible)

        attributeArray.recycle()
    }

    /**
     * @param title : title of the component
     */
    fun setTileTitle(title: String) {
        binding.headerTitle.text = title
        ViewCompat.setAccessibilityHeading(binding.headerTitle, true)
    }

    /**
     * @param supplementary : supplementary information of the component
     */
    fun setTileSupplementary(supplementary: String) {
        binding.subTitle.apply {
            isVisible = true
            text = supplementary
        }
    }

    /**
     * Provides the capability to set the Logo[titleLogo] with adjustable scale[scaleFactor] as Title
     * while preserving the text for a11y needs
     */
    fun setTileTitleLogo(titleLogo: Int, scaleFactor: Float) {
        if (titleLogo != ResourcesCompat.ID_NULL) {
            val svgDrawable = ContextCompat.getDrawable(context, titleLogo)
            svgDrawable?.let {
                val scaledWidth = (it.intrinsicWidth * scaleFactor).toInt()
                val scaledHeight = (it.intrinsicHeight * scaleFactor).toInt()
                it.setBounds(
                    0,
                    0,
                    scaledWidth,
                    scaledHeight
                )
                binding.headerTitle.setCompoundDrawables(svgDrawable, null, null, null)
                binding.headerTitle.setTextColor(Color.TRANSPARENT)
            }
        }
    }

    /**
     * @param badgeCount : Badge count to be shown on the SoP Button
     */

    fun setTileBadgeCount(badgeCount: Int) {
        val badgeSopNormal = NotificationBadge()
        badgeSopNormal.apply {
            addBadge(binding.sopButton, binding.btnSopNormalBackground)
            setBadgeNumber(badgeCount)
        }
        binding.sopButton.setOnClickListener {
            badgeSopNormal.removeBadge()
        }
    }

    fun setSopButton(buttonLabel: String, icon: Drawable?, isVisible: Boolean) {
        binding.sopButton.text = buttonLabel
        binding.sopButton.icon = icon
        binding.sopButton.contentDescription = buttonLabel
        binding.sopButton.visibility = if(isVisible) View.VISIBLE else View.GONE
    }

    /**
     * @return Button as the widget name for the accessibility
     */
    override fun getAccessibilityClassName(): CharSequence {
        return Button::class.java.simpleName
    }

    @SuppressWarnings("Unused")
    fun setActionListener(actionListener: (View) -> Unit) {
        binding.sopButton.setOnClickListener { actionListener.invoke(it) }
    }

}