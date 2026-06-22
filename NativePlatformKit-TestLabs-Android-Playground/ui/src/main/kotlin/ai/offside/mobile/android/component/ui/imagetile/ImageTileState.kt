package ai.offside.mobile.android.component.ui.imagetile

import android.view.View
import androidx.annotation.DrawableRes
import ai.offside.mobile.android.component.ui.R

/**
 * Image Tile has 5 different views collectively based on the supplied data
 *      ImageTile
 *      ImageTileWithInfo
 *      ImageTileWithButton
 *      ImageTileWithIconButton
 *      ImageTileWithListAction
 *
 *  Among all these cards the title and the card image are common
 */
sealed interface ImageTileState {
    val imageUrl: String
    val imageTileTitle: String

    /**
     * Image tile which has the image (33% of the screen) and title at right
     */
    data class ImageTile(
        override val imageUrl: String,
        override val imageTileTitle: String
    ): ImageTileState

    /**
     * Image tile which has the image (33% of the screen) and title at right
     * Info icon with information at the bottom of the card
     */
    data class ImageTileWithInfo(
        override val imageUrl: String,
        override val imageTileTitle: String,
        val infoDescription: String
    ) : ImageTileState

    /**
     * Image tile which has the image (33% of the screen) and title at right
     * Actionable Button at the bottom right
     */
    data class ImageTileWithButton(
        override val imageUrl: String,
        override val imageTileTitle: String,
        val buttonLabel: String
    ) : ImageTileState

    /**
     * Image tile which has the image (33% of the screen) and title at right
     * Actionable button with icon at the right bottom
     */
    data class ImageTileWithStatus(
        override val imageUrl: String,
        override val imageTileTitle: String,
        val status: String,
        @DrawableRes val icon: Int = R.drawable.ic_actionable_caret
    ) : ImageTileState

    /**
     * I image tile which has the image (33% of the screen) and title at right
     * List tile added below the image tile, separated by divider
     */
    data class ImageTileWithListAction(
        override val imageUrl: String,
        override val imageTileTitle: String,
        @DrawableRes val listIcon: Int,
        val actionLabel: String,
        @DrawableRes val rightIcon: Int = R.drawable.ic_actionable_caret
    ) : ImageTileState

    /**
     * I image tile which has the image (33% of the screen) and title at right
     * iCon overlay will be shown on top of image in the tile
     */
    data class ImageTileWithOverlay(
        override val imageUrl: String,
        override val imageTileTitle: String,
        @DrawableRes val icon: Int,
    ) : ImageTileState

    /**
     * get the action label to display if available for empty
     */
    fun getActionButtonLabel() =  when(this) {
        is ImageTileWithButton -> this.buttonLabel
        is ImageTileWithStatus -> this.status
        else -> ""
    }

    /**
     * This function returns the VISIBLE if it is [ImageTileWithButton] otherwise GONE
     */
    fun getButtonVisibility() = if (this is ImageTileWithButton) View.VISIBLE else View.GONE

    /**
     * This function returns the VISIBLE if it is [ImageTileWithInfo] otherwise GONE
     */
    fun getInfoViewVisibility() = if (this is ImageTileWithInfo) View.VISIBLE else View.GONE

    /**
     * This function returns the VISIBLE if it is [ImageTileWithStatus] otherwise GONE
     */
    fun getTileStatusVisibility() = if (this is ImageTileWithStatus) View.VISIBLE else View.GONE

    /**
     * This function returns the VISIBLE if it is [ImageTileWithStatus] otherwise GONE
     */
    fun getListActionVisibility() = if (this is ImageTileWithListAction) View.VISIBLE else View.GONE

    /**
     * This function returns the VISIBLE if it is [ImageTileWithOverlay] otherwise GONE
     */
    fun getOverlayVisibility() = if (this is ImageTileWithOverlay) View.VISIBLE else View.GONE
}