package ai.offside.mobile.android.component.ui.imagetile

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import com.google.android.material.card.MaterialCardView
import ai.offside.mobile.android.component.ui.extensions.addSpaceBetweenCharacter
import ai.offside.mobile.android.component.ui.R
import ai.offside.mobile.android.component.ui.a11y.modifyRoleDescription
import ai.offside.mobile.android.component.ui.databinding.ImageTileBinding

/**
 * Card Tile with Specific Input Field:
 * Card Art Image, Action Button, Radio-Button / Check-box, Link Status, Actionable caret.
 * @param context
 * @param attrs
 * @param defaultStyleAttr
 */
class ImageTile @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defaultStyleAttr: Int = com.google.android.material.R.attr.materialCardViewElevatedStyle
) : MaterialCardView(context, attrs, defaultStyleAttr) {

    private val binding = ImageTileBinding.inflate(LayoutInflater.from(context), this, true)

    /**
     * @param tileState : Tile data and the types
     */
    fun setImageTileData(tileState: ImageTileState) {
        binding.imageTile = tileState
        when (tileState) {
            is ImageTileState.ImageTileWithInfo -> binding.tileInfoBox.setInfoText(tileState.infoDescription)
            is ImageTileState.ImageTileWithListAction -> binding.tileListDescription.text = tileState.actionLabel
            is ImageTileState.ImageTileWithOverlay -> binding.overlayArtImage.setImageResource(tileState.icon)
            else -> Unit
        }
        // Set the content description and the tree traversal on accessibility
        setAccessibilityContent(tileState)
    }

    /**
     * @param tileState : [ImageTileActionState] state to define the click action to be added for the specific view
     * @param actionListener : click action listener of the view
     */
    fun setActionListener(tileState: ImageTileActionState, actionListener: ((View) -> Unit)) {
        when (tileState) {
            ImageTileActionState.IMAGE_TILE_LIST -> binding.tileListAction.setOnClickListener { actionListener.invoke(it) }
            ImageTileActionState.IMAGE_TILE_STATUS ->  binding.cardTileItemCard.setOnClickListener { actionListener.invoke(it) }
            ImageTileActionState.IMAGE_TILE_BUTTON ->  {
                binding.cardTileItemCard.setOnClickListener { actionListener.invoke(it) }
                binding.tileActionButton.setOnClickListener { actionListener.invoke(it) }
            }
        }
    }

    /**
     * This function sets the content description to be read on accessibility enabled state
     * @param tileState : Tile data to be rendered
     */
    private fun setAccessibilityContent(tileState: ImageTileState) {
        val cardTitleContentDescription = getCardTitleContentDescription(context, tileState.imageTileTitle)
        binding.tilePrimaryInfo.contentDescription = cardTitleContentDescription
        when (tileState) {
            is ImageTileState.ImageTileWithButton -> {
                binding.cardTileItemCard.modifyRoleDescription(cardTitleContentDescription.plus(", ${tileState.buttonLabel}"), Button::class.java.simpleName)
            }
            is ImageTileState.ImageTileWithStatus -> {
                binding.cardTileItemCard.modifyRoleDescription(cardTitleContentDescription.plus(", ${tileState.status}"), Button::class.java.simpleName)
            }
            is ImageTileState.ImageTileWithListAction -> {
                binding.tileListAction.modifyRoleDescription(String.format(
                    resources.getString(R.string.label_for_content_description_format),
                    tileState.actionLabel,
                    cardTitleContentDescription
                ), Button::class.java.simpleName)
            }
            else -> Unit
        }
    }

    /**
     * @param context : context required to read the string string format
     * @param cardTitle: Card title includes the number,
     *
     * @return String : formatted content description to read the title as 'card <title> ending <card-number>
     */
    private fun getCardTitleContentDescription(context: Context, cardTitle: String): String {
        val cartName = cardTitle.substring(0, cardTitle.length - (CARD_NUMBER_LENGTH_IN_TITLE + 1))
        val cardNumber = cardTitle.substring(cardTitle.length - CARD_NUMBER_LENGTH_IN_TITLE).addSpaceBetweenCharacter()
        return context.resources.getString(R.string.card_title_content_description_format, cartName, cardNumber)
    }

    companion object {
        const val CARD_NUMBER_LENGTH_IN_TITLE = 4
    }
}