package ai.offside.mobile.android.component.ui.badges

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.InfoBadgeLayoutBinding
import com.squareup.picasso.Picasso
import timber.log.Timber

/**
 *  InfoBadge
 *  @param context
 *  @param attrs - Custom attributes defined as [R.styleable.InfoBadge)]
 *  badgeIcon - Required for Custom Badge Image
 *  badgeContentDescription - Required for Custom Badge
 */
class InfoBadge @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(
    context, attrs
) {
    private val binding = InfoBadgeLayoutBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val attributeArray = context.obtainStyledAttributes(attrs, R.styleable.InfoBadge)
        val badgeType = attributeArray.getInt(R.styleable.InfoBadge_infoBadgeType, InfoBadgeType.SUCCESS.ordinal)
        val badgeWidth = attributeArray.getInt(R.styleable.InfoBadge_infoBadgeWidth, InfoBadgeWidth.FIXED.ordinal)
        val badgeUrl = attributeArray.getString(R.styleable.InfoBadge_infoBadgeUrl)

        when (badgeType) {

            InfoBadgeType.SUCCESS.ordinal -> {
                binding.infoBadge.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_general_badge_success
                    )
                )
                binding.infoBadge.contentDescription =
                    context.resources.getString(R.string.info_badge_success)
            }

            InfoBadgeType.WARNING.ordinal -> {
                binding.infoBadge.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_general_badge_warning
                    )
                )
                binding.infoBadge.contentDescription =
                    context.resources.getString(R.string.info_badge_warning)
            }

            InfoBadgeType.CHECKMARK.ordinal -> {
                binding.infoBadge.setImageDrawable(
                    AppCompatResources.getDrawable(
                        context,
                        R.drawable.ic_general_badge_checkmark
                    )
                )
                binding.infoBadge.contentDescription =
                    context.resources.getString(R.string.info_badge_checkmark)
            }

            else -> {
                val iconPadding = resources.getDimension(R.dimen.padding_xsmall).toInt()
                binding.infoBadge.apply {
                    setImageDrawable(attributeArray.getDrawable(R.styleable.InfoBadge_infoBadgeIcon))
                    contentDescription = attributeArray.getString(R.styleable.InfoBadge_infoBadgeContentDescription)
                    setContentPadding(iconPadding, iconPadding, iconPadding, iconPadding)
                }
            }
        }
        if (badgeWidth == InfoBadgeWidth.FIXED.ordinal) {
            val widthInPercentage = calculateWidthInPercentage(badgeType)
            if (badgeType == InfoBadgeType.CUSTOM.ordinal) {
                binding.root.layoutParams.apply {
                    width = widthInPercentage
                    height = widthInPercentage
                }
            } else {
                binding.infoBadge.layoutParams.apply {
                    width = widthInPercentage
                    height = widthInPercentage
                }
            }
        }

        loadImageUrl(badgeUrl)
        attributeArray.recycle()
    }

    /**
     *  Calculate the Width of the Badge by Percentage value of device Width
     *  @param badgeType - SUCCESS/WARNING/CUSTOM
     *  @return calculated Width for Badge View
     */
    private fun calculateWidthInPercentage(badgeType: Int): Int {
        val deviceWidth = context.resources.displayMetrics.widthPixels
        return when (badgeType) {
            0, 1, 2 -> deviceWidth * SUCCESS_BADGE_WIDTH_IN_PERCENTAGE
            else -> deviceWidth * CUSTOM_BADGE_WIDTH_IN_PERCENTAGE
        }.toInt()
    }

    /**
     *  Set Custom Image for InfoBadge
     */
    fun setImage(drawable: Drawable) {
        binding.infoBadge.setImageDrawable(drawable)
    }

    /**
     *  load image from url
     *
     *  @param imageUrl url from attribute
     */
    fun loadImageUrl(imageUrl: String?) {
        if (imageUrl.isNullOrEmpty()) {
            return
        }
        try {
            Picasso.get().load(imageUrl).into(binding.infoBadge)
        } catch (e: Exception) {
            Timber.e(e)
        }
    }

    /**
     * Set ContentDescription for Custom Image
     */
    fun setContentDescription(contentDescription: String) {
        binding.infoBadge.contentDescription = contentDescription
    }

    /**
     * Set tint color for image
     */
    fun setTint(tintList: ColorStateList?) {
        binding.infoBadge.imageTintList = tintList
    }

    companion object {
        private const val CUSTOM_BADGE_WIDTH_IN_PERCENTAGE = 0.19
        private const val SUCCESS_BADGE_WIDTH_IN_PERCENTAGE = 0.16
    }

}

