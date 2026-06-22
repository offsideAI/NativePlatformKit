package ai.offside.mobile.android.component.ui.tile.deposit

import android.view.View
import androidx.annotation.IntRange


/**
 * There are 2 instances of this object within [DepositActivityTileData]
 *   which is used to provide and customize a *ROW* of text.  The designation
 *   as to *which particular row* in the design will be denoted by the name
 *   of the field in the [DepositActivityTileData]
 */
interface DepositActivityTileTextRow {
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
 * To customize the rendered [android.view.View], this objects to
 *   drive a [DepositActivityTileDataModel] instance which,
 *   in turn, drives the rendered [android.view.View]
 */
interface DepositActivityTileData<T> {
    /** Source of type [T] */
    val tileData: T
    /** The top-most row of the UI */
    val primaryTextRow: DepositActivityTileTextRow
    /** The bottom-most row of the UI */
    val secondaryTextRow: DepositActivityTileTextRow

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
 * The actual object bound to the layout.  Implements [DepositActivityTileData]
 *   by delegation to [data]
 * @param data An instance of the [DepositActivityTileData]
 */
class DepositActivityTileDataModel<T> constructor(
    private val data: DepositActivityTileData<T>
) : DepositActivityTileData<T> by data {

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

    /**Returns boolean if secondaryTile row ( contains both of the secondary starting and ending) is visible/gone */
    val isSecondaryTextRowGone : Boolean
        get() = !data.secondaryTextRow.startingEnabled && !data.secondaryTextRow.endingEnabled
}
