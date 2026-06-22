package ai.offside.mobile.android.component.ui.tile.reviewfield

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.ReviewFieldTileBinding

/**
 * @param context
 * @param attributeSet : [AttributeSet] also holds the custom component data defined as [R.styleable.ReviewFieldTile]
 *  - titleLabel
 *  - titleValue
 *  - titleSupplementary
 *  - titleActionIcon
 *  - titleActionContentDescription
 *  - titleActionIconTint
 *  Custom attributes can be string reference or data-binding
 */
class ReviewFieldTile(
    context: Context,
    attributeSet: AttributeSet? = null
): ConstraintLayout(context, attributeSet) {

    val binding: ReviewFieldTileBinding = ReviewFieldTileBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        val customAttributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.ReviewFieldTile)

        setTitleLabel(customAttributeSet.getString(R.styleable.ReviewFieldTile_titleLabel).orEmpty())
        setTitleValue(customAttributeSet.getString(R.styleable.ReviewFieldTile_titleValue).orEmpty())

        // Tile Supplementary Information
        customAttributeSet.getString(R.styleable.ReviewFieldTile_titleSupplementary)?.let {
            setTitleSupplementary(it)
        }

        // Tile Action Icon
        customAttributeSet.getDrawable(R.styleable.ReviewFieldTile_titleActionIcon)?.let {
            binding.tileActionButton.apply {
                isVisible = true
                this.icon = it
                contentDescription = TextUtils.concat(
                    customAttributeSet.getString(R.styleable.ReviewFieldTile_titleActionContentDescription).orEmpty(),
                    " ",
                    binding.reviewLabel.text
                )
            }
        }
        //Set action icon's tint color
        if (customAttributeSet.hasValue(R.styleable.ReviewFieldTile_titleActionIconTint)) {
            binding.tileActionButton.iconTint =
                customAttributeSet.getColorStateList(R.styleable.ReviewFieldTile_titleActionIconTint)
        }


        customAttributeSet.recycle()
    }

    /**
     * @param label: Tile Label, can be accessed from data-binding as app:titleLabel
     */
    fun setTitleLabel(label: String) {
        binding.reviewLabel.text = label
    }

    /**
     * @param value: Tile Value, can be accessed from data-binding as app:titleValue
     */
    fun setTitleValue(value: String) {
        binding.reviewValue.text = value
    }

    /**
     * @param contentDescription content description for the value to be called out
     */
    fun setReviewValueContentDescription(contentDescription: String) {
        binding.reviewValue.contentDescription = contentDescription
    }

    /**
     * @param label: Tile Label, can be accessed from data-binding as app:titleSupplementary
     */
    fun setTitleSupplementary(supplementary: String) {
        binding.reviewInfo.apply {
            isVisible = true
            text = supplementary
        }
    }

    /**
     * @param contentDescription: content description to call-out the action like "edit label, double tap to activate"
     */
    fun setTitleActionContentDescription(contentDescription: String) {
        binding.tileActionButton.contentDescription = TextUtils.concat(contentDescription, binding.reviewLabel.text)
    }

    /**
     * @param actionListener : Action listener for the tile action view
     */
    fun setActionListener(actionListener: (View) -> Unit) {
        binding.tileActionButton.setOnClickListener { actionListener.invoke(it) }
    }

}