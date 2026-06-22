package ai.offside.mobile.android.component.ui.tile

import android.view.View
import androidx.annotation.IntRange

/** Each button will be individually customizable by this object */
interface PaymentActivityTileButton<T> {
    /** Enable or suppress the button */
    val visible: Boolean
    /** Enable or disable the click event */
    val enabled: Boolean
    /** The [String] to show on the button */
    val text: String
    /**
     * Bound to the button's click handler
     * @param button The [View] with which the user
     *   interacted
     * @param tileData The particular [PaymentActivityTileData.tileData]
     *   of type [T]
     */
    fun onButtonClick(
        button: View,
        tileData: T
    )
}

/**
 * There are 2 instances of this object within [PaymentActivityTileData]
 *   which is used to provide and customize a *ROW* of text.  The designation
 *   as to *which particular row* in the design will be denoted by the name
 *   of the field in the [PaymentActivityTileData]
 */
interface PaymentActivityTileTextRow {
    /** Text shown with the start-alignment set to the parent-start */
    val starting: String
    /** Suppress or show the [starting] [String] */
    val startingEnabled: Boolean
    /** Text shown with the end-alignment set to the parent-end */
    val ending: String
    /** Suppress or show the [ending] [String] */
    val endingEnabled: Boolean
}

/**
 * To customize the rendered [android.view.View], this object,
 *   along with [PaymentActivityTileButton]s, are used to
 *   drive a [PaymentActivityTileDataModel] instance which,
 *   in turn, drives the rendered [android.view.View]
 */
interface PaymentActivityTileData<T> {
    /** Source of type [T] */
    val tileData: T
    /** TODO: Is this appropriate? */
    val imageSource: String

    /** The top-most row of the UI */
    val primaryTextRow: PaymentActivityTileTextRow
    /** The bottom-most row of the UI */
    val secondaryTextRow: PaymentActivityTileTextRow

    /** The primary CTA */
    val primaryButton: PaymentActivityTileButton<T>
    /** The alternate CTA */
    val secondaryButton: PaymentActivityTileButton<T>

    /**
     * The click handler on the entire [View]
     * @param tileView The container [View]
     * @param tileData The [tileData] of type [T]
     */
    fun onTileClick(
        tileView: View,
        tileData: T
    )
}

/**
 * The actual object bound to the layout.  Implements [PaymentActivityTileData]
 *   by delegation to [data]
 * @param data An instance of the [PaymentActivityTileData]
 */
internal class PaymentActivityTileDataModel<T> constructor(
    private val data: PaymentActivityTileData<T>
) : PaymentActivityTileData<T> by data {
    /** Exposes [PaymentActivityTileData.primaryButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val primaryButtonVisibility: Int
        get() = when {
            data.primaryButton.visible -> View.VISIBLE
            else -> View.GONE
        }

    /** Exposes [PaymentActivityTileData.secondaryButton] visibility */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryButtonVisibility: Int
        get() = when {
            data.secondaryButton.visible -> View.VISIBLE
            else -> View.GONE
        }

    /** Returns a particular [Int] for displaying layout elements */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryTextRowStartingVisibility: Int
        get() = when {
            data.secondaryTextRow.startingEnabled -> View.VISIBLE
            else -> View.GONE
        }

    /** Returns a particular [Int] for displaying layout elements */
    @get:IntRange(from = View.VISIBLE.toLong(), to = View.GONE.toLong())
    val secondaryTextRowEndingVisibility: Int
        get() = when {
            data.secondaryTextRow.endingEnabled -> View.VISIBLE
            else -> View.GONE
        }
}