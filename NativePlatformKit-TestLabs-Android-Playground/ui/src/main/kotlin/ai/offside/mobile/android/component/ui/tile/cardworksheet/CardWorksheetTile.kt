package ai.offside.mobile.android.component.ui.tile.cardworksheet

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.databinding.CardWorksheetTileBinding
import ai.offside.mobile.android.component.ui.extensions.setTextWithMiddleEllipses

/**
 * @param context
 * @param attributeSet Will contain the custom styleable AttributeSet, they are listed below
 * [R.styleable.CardWorksheetTile]
 *      artUrl : card image URL link
 *      cardTitle : Title of the card worksheet
 *      cardTitleSuffix : Suffix text, if exists then middle ellipse will be applied to Title. Define after app:cardWorksheetTitle
 *      cardSupplementary : Supplementary text
 *      cardSupplementaryDescription : Supplementary custom content description
 *      cardSecondary : Secondary test
 *      cardSecondaryDescription : Secondary custom content description
 */
class CardWorksheetTile(
    context: Context,
    attributeSet: AttributeSet? = null
) : ConstraintLayout(context, attributeSet) {

    val binding: CardWorksheetTileBinding = CardWorksheetTileBinding.inflate(LayoutInflater.from(context), this, true)
    init {
        val attributeArray = context.obtainStyledAttributes(attributeSet, R.styleable.CardWorksheetTile, 0, 0)
        // Card worksheet primary title information
        attributeArray.getString(R.styleable.CardWorksheetTile_cardTitle)?.let { title ->
            val cardWorksheetTitleSuffix = attributeArray.getString(R.styleable.CardWorksheetTile_cardTitleSuffix)
            setCardTitleEllipses(title, cardWorksheetTitleSuffix)
        }

        // Card worksheet supplementary information
        setCardSupplementary(attributeArray.getString(R.styleable.CardWorksheetTile_cardSupplementary).orEmpty())

        attributeArray.getString(R.styleable.CardWorksheetTile_cardSupplementaryDescription)?.let {
            setCardSupplementaryDescription(it)
        }

        // Card worksheet secondary information
        attributeArray.getString(R.styleable.CardWorksheetTile_cardSecondary)?.let {
            setCardSecondary(it)
        }
        attributeArray.getString(R.styleable.CardWorksheetTile_cardSecondaryDescription)?.let {
            setCardSecondaryDescription(it)
        }
        attributeArray.getString(R.styleable.CardWorksheetTile_artUrl)?.let {
            setArtUrl(it)
        }
        attributeArray.recycle()
    }

    /**
     * @param imageUrl : card worksheet image to be loaded
     */
    fun setArtUrl(imageUrl: String) {
        binding.tileArtImage.setImageUrl(imageUrl)
    }

    /**
     * @param title: primary information of the tile (title)
     */
    fun setCardTitle(title: String) {
        binding.tilePrimaryInfo.text = title
    }

    /**
     * @param suffix: primary suffix information of the tile (title), middle ellipsis applied
     *  set this after app:cardWorksheetTitle to be applied with title
     */
    fun setCardTitleSuffix(suffix: String?) {
        suffix?.let {
            setCardTitleEllipses(binding.tilePrimaryInfo.text.toString(), it)
        }
    }

    /**
     * @param title: primary information of the tile (title)
     * @param suffix: primary suffix information of the tile (title), middle ellipses applied
     */
    private fun setCardTitleEllipses(title: String, suffix: String? = null) {
        suffix?.let { suffixInfo ->
            binding.tilePrimaryInfo.setTextWithMiddleEllipses(title, suffixInfo)
        } ?: run {
            binding.tilePrimaryInfo.text = title
        }
    }

    /**
     * @param supplementary : Supplementary information of the card (below title)
     */
    fun setCardSupplementary(supplementary: String?) {
        binding.tileSupplementaryInfo.text = supplementary.orEmpty()
    }

    /**
     * @param secondaryInfo : Secondary information of the card (bottom)
     */
    fun setCardSecondary(secondaryInfo: String?) {
        secondaryInfo?.let {
            binding.tileSecondaryInfo.apply {
                isVisible = true
                text = secondaryInfo
            }
        }
    }

    /**
     * @param contentDescription : custom content description to the title view
     */
    fun setCardTitleContentDescription(contentDescription: String) {
        binding.tilePrimaryInfo.contentDescription = contentDescription
    }

    /**
     * @param contentDescription : custom content description to the Supplementary view
     */
    fun setCardSupplementaryDescription(contentDescription: String) {
        binding.tileSupplementaryInfo.contentDescription = contentDescription
    }

    /**
     * @param contentDescription : custom content description to the Secondary view
     */
    fun setCardSecondaryDescription(contentDescription: String) {
        binding.tileSecondaryInfo.contentDescription = contentDescription
    }
}