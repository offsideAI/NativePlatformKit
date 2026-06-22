package ai.offside.mobile.android.component.ui.tile.feature

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.annotation.OptIn
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.badge.BadgeUtils
import com.google.android.material.badge.ExperimentalBadgeUtils
import com.google.android.material.color.MaterialColors
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.FeatureTileBinding

/**
 * @param context
 * @param attributeSet : [AttributeSet] also holds the custom component data defined as [R.styleable.FeatureTile]
 *  - primaryIcon : Mandatory, default is empty
 *  - secondaryIcon : Default will be menu-dots
 *  - tileTitle : Title of the tile
 *  - tileSupplementary : Supplementary information, default will be GONE state
 *  - tileBadgeCount : Tile badge count, provide tilBadgeContentDescription for accessibility
 *  - tileDivider : Default the divider will be VISIBLE, otherwise set it to false
 *
 *  The badge can be added dynamically from program using [setTileBadgeCount]
 */
@OptIn(ExperimentalBadgeUtils::class)
class FeatureTile(
    context: Context,
    attributeSet: AttributeSet? = null
): ConstraintLayout(context, attributeSet) {

    private val binding: FeatureTileBinding = FeatureTileBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        val attributeArray = context.obtainStyledAttributes(attributeSet, R.styleable.FeatureTile, 0, 0)

        setTileTitle(attributeArray.getString(R.styleable.FeatureTile_tileTitle).orEmpty())

        attributeArray.getString(R.styleable.FeatureTile_tileSupplementary)?.let {
            setTileSupplementary(it)
        }

        attributeArray.getDrawable(R.styleable.FeatureTile_primaryIcon)?.let {
            binding.icon.setImageDrawable(it)
        }

        attributeArray.getDrawable(R.styleable.FeatureTile_secondaryIcon)?.let {
            binding.caretIcon.apply {
                isVisible = true
                setImageDrawable(it)
            }
            binding.moreMenu.visibility = View.GONE
        }
        // Default the divider will be visible, set it to false otherwise
        binding.tileDivider.isVisible = attributeArray.getBoolean(R.styleable.FeatureTile_tileDivider, true)
        // Adding Badge to the tile
        setTileBadgeCount(attributeArray.getInteger(R.styleable.FeatureTile_tileBadgeCount, 0))
        attributeArray.recycle()
    }

    /**
     * As per the A11y expectations the badge count to be read at the end, like: <title><supplementary><badge-count>
     */
    private fun updateTileContentDescription() {
        var contentDescription = binding.tileTitle.text.toString()
        binding.supplementaryInfo.text?.let {
            contentDescription = contentDescription.plus(", ").plus(it)
        }
        binding.badgeIconContainer.contentDescription?.let {
            contentDescription = contentDescription.plus(", ").plus(binding.badgeIconContainer.contentDescription)
        }
        binding.moreMenu.contentDescription?.let {
            contentDescription = contentDescription.plus(", ").plus(binding.moreMenu.contentDescription)
        }
        binding.root.modifyRoleDescription(contentDescription, Button::class.java.simpleName, ViewGroup.FOCUS_BEFORE_DESCENDANTS)
    }


    /**
     * @param title : title of the component
     */
    fun setTileTitle(title: CharSequence) {
        binding.tileTitle.text = title
        updateTileContentDescription()
    }

    /**
     * @param supplementary : supplementary information of the component
     */
    fun setTileSupplementary(supplementary: String) {
        binding.supplementaryInfo.apply {
            isVisible = true
            text = supplementary
        }
        updateTileContentDescription()
    }

    /**
     * @param badgeCount : Badge count to be shown on the tile primary icon
     */
    fun setTileBadgeCount(badgeCount: Int) {
        if (badgeCount > 0) {
            val badgeDrawable = BadgeDrawable.create(context)
            badgeDrawable.number = badgeCount
            badgeDrawable.isVisible = true
            badgeDrawable.horizontalOffset = resources.getDimension(R.dimen.feature_tile_badge_offset).toInt()
            badgeDrawable.verticalOffset = resources.getDimension(R.dimen.feature_tile_badge_offset).toInt()
            badgeDrawable.backgroundColor = MaterialColors.getColor(context, R.attr.offside_error, ContextCompat.getColor(context, R.color.sys_color_neutral100))
            badgeDrawable.badgeTextColor = MaterialColors.getColor(context, R.attr.offside_onError, ContextCompat.getColor(context, R.color.sys_color_red60))
            binding.badgeIconContainer.foreground = badgeDrawable
            binding.badgeIconContainer.addOnLayoutChangeListener { _, _, _, _, _, _, _, _, _ ->
                BadgeUtils.attachBadgeDrawable(
                    badgeDrawable,
                    binding.icon,
                    binding.badgeIconContainer
                )
            }
            binding.badgeIconContainer.contentDescription = badgeCount.toString()
                .plus(" ") // add space between count and label
                .plus(resources.getQuantityString(R.plurals.badge_notifications_content_description, badgeCount))
            updateTileContentDescription()
        }
    }

    /**
     * @param tileActionListener : OnClick Event listener for whole tile
     */
    fun setTileActionListener(tileActionListener: (view: View) -> Unit) {
        binding.root.isClickable = true
        binding.root.setOnClickListener {
            tileActionListener.invoke(it)
        }
    }
    /**
     * @param drawable : Sets the icon and updates the accessibility
     */
    fun setPrimaryIcon(drawable: Drawable) {
        binding.icon.setImageDrawable(drawable)
        updateTileContentDescription()
    }
    /**
     * @param isVisible : Toggles visibility for divider
     */
    fun setDividerVisibility(isVisible: Boolean) {
        binding.tileDivider.isVisible = isVisible
        updateTileContentDescription()
    }

    /**
     * Sets the action listener to More Menu and updates the accessibility
     * Adds accessibility talkback action to main content view of slider
     *
     * @param moreMenuActionListener : OnClick Event listener
     */
    fun setMoreMenuActionListener(moreMenuActionListener: (view: View) -> Unit) {
        binding.moreMenu.setOnClickListener {
            moreMenuActionListener.invoke(it)
        }
        ViewCompat.addAccessibilityAction(
            binding.root,
            context.getString(R.string.feature_tile_more_menu_action)
        ) { view, _ ->
            moreMenuActionListener.invoke(view)
            true
        }
    }
}